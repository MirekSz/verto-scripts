package tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;

import pl.com.stream.lib.commons.util.StringUtil;
import pl.com.stream.verto.adm.asen.tools.server.pub.script.repo.FileOperator;
import pl.com.stream.verto.adm.asen.tools.server.pub.script.repo.RemoteScriptRepositoryAdapter;
import pl.com.stream.verto.adm.asen.tools.server.pub.script.repo.ScriptDataInfo;
import pl.com.stream.verto.adm.asen.tools.server.pub.script.repo.ScriptType;
import pl.com.stream.verto.adm.asen.tools.server.pub.script.repo.SourceGenerator;

public class SynchronizationController {

	private static final String APP_LOCATION_KEY = "appLocation";
	private static final String PASSWORD_KEY = "password";
	private static final String LOGIN_KEY = "login";
	public static String login;
	public static String password;
	public static String appLocation;

	static List<ScriptDataInfo> dataList;

	private static RemoteScriptRepositoryAdapter connector;
	private static Thread hotDeployingThread;

	public static void main(final String[] args) {
		ScriptsTable.setLookAndFeel();
		tryLoadConnectionInfo();

		List<ScriptDataInfo> list = loadScripts();
		List<Long> localFiles = findLocalScripts(list);
		final ScriptsTable scriptsTable = new ScriptsTable(list, localFiles);
		addConnectionDataInfoChange(scriptsTable);

		setFindForEditActionListener(scriptsTable);

		setUpdateActionListener(scriptsTable);

		setHotDeployingActionListener(scriptsTable);

		setScriptTypeChangeListener(scriptsTable);
	}

	private static void setScriptTypeChangeListener(
			final ScriptsTable scriptsTable) {
		scriptsTable.getScriptType().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ScriptType scriptType = (ScriptType) scriptsTable
						.getScriptType().getSelectedItem();
				List<ScriptDataInfo> list = connector.getAllScripts(scriptType);
				scriptsTable.fireModelChange(list);
				scriptsTable.setLocalFiles(findLocalScripts(list));
			}
		});

	}

	private static void setHotDeployingActionListener(
			final ScriptsTable scriptsTable) {
		scriptsTable.getHotDeployinhButton().addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (!scriptsTable.getHotDeployinhButton().isSelected()) {
							hotDeployingThread.stop();
						} else {
							try {
								saveSelectedFiles(scriptsTable);
							} catch (Exception e) {
								e.printStackTrace();
							}
							scriptsTable.setState(JFrame.ICONIFIED);
							List<ScriptDataInfo> selectedObjects = scriptsTable
									.getSelectedObjects();
							hotDeployingThread = new Thread(new HotDeployer(
									selectedObjects));
							hotDeployingThread.start();
						}
					}
				});

	}

	private static void setUpdateActionListener(final ScriptsTable scriptsTable) {
		scriptsTable.getSaveOnServerButton().addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent e) {
						updateScripts(scriptsTable.getSelectedObjects());
						System.exit(0);
					}

				});
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				scriptsTable.setVisible(true);
			}
		});
	}

	private static void setFindForEditActionListener(
			final ScriptsTable scriptsTable) {
		scriptsTable.getGetForEditButton().addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							saveSelectedFiles(scriptsTable);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						System.exit(0);
					}
				});

	}

	private static void saveSelectedFiles(final ScriptsTable scriptsTable)
			throws Exception {
		for (ScriptDataInfo scriptDataInfo : scriptsTable.getSelectedObjects()) {
			SourceGenerator sourceGenerator = new SourceGenerator(
					scriptDataInfo);
			FileOperator fileOperator = new FileOperator(
					scriptsTable.getAllLocation(), scriptDataInfo.getName(),
					scriptDataInfo.getType());
			String groovyClassSource = sourceGenerator
					.getGroovyClassSource("host_"
							+ new URL(appLocation).getHost());
			if (!fileOperator.fileExists()) {
				fileOperator.saveToFile(groovyClassSource);
			}
		}
	}

	private static void addConnectionDataInfoChange(
			final ScriptsTable scriptsTable) {
		scriptsTable
				.setConnectionDataInfoChangeListener(new ConnectionDataChangeListener() {

					@Override
					public void onChange(final String login,
							final String password, final String appLocation) {
						SynchronizationController.login = login;
						SynchronizationController.password = password;
						SynchronizationController.appLocation = appLocation;
						storeConnectionInfo();
						connector = new RemoteScriptRepositoryAdapter(
								appLocation, login, password);
						scriptsTable.fireModelChange(connector
								.getAllScripts(ScriptType.CLIENT));

					}

				});
	}

	private static List<Long> findLocalScripts(final List<ScriptDataInfo> list) {
		List<Long> result = new ArrayList<Long>();
		for (ScriptDataInfo scriptDataInfo : list) {
			FileOperator operator = new FileOperator(appLocation,
					scriptDataInfo.getName(), scriptDataInfo.getType());
			if (operator.fileExists()) {
				result.add(scriptDataInfo.getId());
			}
		}
		return result;
	}

	private static List<ScriptDataInfo> loadScripts() {
		try {
			if (StringUtil.isNotEmpty(login) && StringUtil.isNotEmpty(password)
					&& StringUtil.isNotEmpty(appLocation)) {
				connector = new RemoteScriptRepositoryAdapter(appLocation,
						login, password);
				return connector.getAllScripts(ScriptType.CLIENT);
			}
		} catch (Exception e) {
			connector = null;
		}
		return new ArrayList<ScriptDataInfo>();
	}

	private static void storeConnectionInfo() {
		String property = System.getProperty("java.io.tmpdir");
		File file = new File(property + File.separator + "groovy.properties");
		Properties properties = new Properties();
		properties.put(LOGIN_KEY, login);
		properties.put(PASSWORD_KEY, password);
		properties.put(APP_LOCATION_KEY, appLocation);
		try {
			properties.store(new FileOutputStream(file), "");
		} catch (Exception e) {
		}
	}

	private static void tryLoadConnectionInfo() {
		String property = System.getProperty("java.io.tmpdir");
		File file = new File(property + File.separator + "groovy.properties");
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(file));
			login = properties.getProperty(LOGIN_KEY);
			password = properties.getProperty(PASSWORD_KEY);
			appLocation = properties.getProperty(APP_LOCATION_KEY);
		} catch (Exception e) {
		}
	}

	private static void updateScripts(final List<ScriptDataInfo> scripts) {
		for (ScriptDataInfo scriptDataInfo : scripts) {
			updateScript(scriptDataInfo);
		}
		connector.logout();
	}

	public static void updateScript(ScriptDataInfo scriptDataInfo) {
		SourceGenerator sourceGenerator = new SourceGenerator(scriptDataInfo);
		FileOperator fileOperator = createFileOperator(
				scriptDataInfo.getName(), scriptDataInfo.getType());
		String readFromFile = fileOperator.readFromFile();
		String groovyScriptSource = sourceGenerator
				.getGroovyScriptSource(readFromFile);
		RemoteScriptRepositoryAdapter adapter = new RemoteScriptRepositoryAdapter(
				appLocation, login, password);
		scriptDataInfo.setSource(groovyScriptSource);
		String packageName = readFromFile.split("\n")[0].trim();
		scriptDataInfo.setParams(new ArrayList<String>());
		// scriptDataInfo.getParams().add(
		// fileOperator.getFileName().replace(".groovy", ""));
		scriptDataInfo.getParams().add(fileOperator.getFileName());
		scriptDataInfo.getParams().add(packageName);

		adapter.update(scriptDataInfo);
	}

	private static FileOperator createFileOperator(final String fileName,
			final ScriptType scriptType) {
		return new FileOperator(appLocation, fileName, scriptType);
	}
}
