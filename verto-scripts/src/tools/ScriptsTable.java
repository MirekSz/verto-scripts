package tools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import pl.com.stream.lib.commons.util.StringUtil;
import pl.com.stream.verto.adm.asen.tools.server.pub.script.repo.ScriptDataInfo;
import pl.com.stream.verto.adm.asen.tools.server.pub.script.repo.ScriptType;

public class ScriptsTable extends javax.swing.JFrame {

	private final JTextField loginField = new javax.swing.JTextField(
			SynchronizationController.login == null ? "login"
					: SynchronizationController.login);
	private final JPasswordField passwordField = new javax.swing.JPasswordField(
			SynchronizationController.password == null ? "password"
					: SynchronizationController.password);
	private final JTextField appLocationField = new javax.swing.JTextField(
			SynchronizationController.appLocation == null ? "http://localhost:7070/"
					: SynchronizationController.appLocation);
	private JComboBox scriptType = new JComboBox(new ScriptType[] {
			ScriptType.CLIENT, ScriptType.SERVER, ScriptType.TECHNICAL_EVENT,
			ScriptType.BUSINESS_EVENT });
	private final javax.swing.JButton getForEditButton = new JButton(
			"Pobierz do edycji");
	private final javax.swing.JToggleButton hotDeployinhButton = new JToggleButton(
			"Hot deploying");
	private final javax.swing.JButton saveOnServerButton = new JButton(
			"Popraw na serwerze");
	private javax.swing.JScrollPane tableScrollPanel = new javax.swing.JScrollPane();
	private javax.swing.JTable table;
	private final List<ScriptDataInfo> list;
	private List<Long> selectedIds = new ArrayList<Long>();
	List<Long> localFiles;

	public ScriptsTable(final List<ScriptDataInfo> list,
			final List<Long> localFiles) {
		this.list = list;
		this.localFiles = localFiles;
		initComponents();
		setSize(1000, 500);
	}

	FocusListener dataChangeGenerator = new FocusListener() {

		@Override
		public void focusLost(final FocusEvent e) {
			String login = loginField.getText();
			String password = passwordField.getText();
			String location = appLocationField.getText();
			if (!StringUtil.isEmpty(login) && !StringUtil.isEmpty(password)
					&& !StringUtil.isEmpty(location)) {
				connectionDataInfoChangeListener.onChange(login, password,
						location);
			}
		}

		@Override
		public void focusGained(final FocusEvent e) {

		}
	};
	private ConnectionDataChangeListener connectionDataInfoChangeListener;

	@SuppressWarnings("unchecked")
	private void initComponents() {
		appLocationField.addFocusListener(dataChangeGenerator);

		setTable(new javax.swing.JTable() {

			@Override
			public Class getColumnClass(final int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return Boolean.class;
				case 2:
					return Boolean.class;
				case 3:
					return Boolean.class;
				default:
					return Boolean.class;
				}
			}
		});
		tableScrollPanel = new javax.swing.JScrollPane();

		getTable().setModel(
				new javax.swing.table.DefaultTableModel(new String[] { "Nazwa",
						"Aktywny", "Pobrany", "Wybierz", }, getList().size()) {

					@Override
					public boolean isCellEditable(final int row,
							final int column) {
						return column == 3;
					}

					@Override
					public void setValueAt(final Object aValue, final int row,
							final int column) {
						if (Boolean.TRUE.equals(aValue)) {
							getSelectedIds().add(getList().get(row).getId());
						} else {
							getSelectedIds().remove(getList().get(row).getId());
						}
						super.setValueAt(aValue, row, column);
					}

					@Override
					public Object getValueAt(final int row, final int column) {
						ScriptDataInfo scriptDataInfo = getList().get(row);
						if (column == 1) {
							return scriptDataInfo.getActive();
						}
						if (column == 2) {
							return Boolean.TRUE.equals(localFiles
									.contains(scriptDataInfo.getId()));
						}
						if (column == 3) {
							return Boolean.TRUE.equals(getSelectedIds()
									.contains(scriptDataInfo.getId()));
						}
						return scriptDataInfo.getName();
					}
				});
		tableScrollPanel.setViewportView(getTable());

		setNetbeansLayoutHacks();
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		setCentered();
		setTitle("DISEN");
		table.getColumnModel().getColumn(0).setPreferredWidth(400);

		table.setRowHeight(20);
		table.setFillsViewportHeight(true);

		pack();

	}// </editor-fold>

	private void setNetbeansLayoutHacks() {
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		getScriptType(),
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		174,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(5, 5, 5))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		tableScrollPanel,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		647,
																		Short.MAX_VALUE)
																.addGap(13, 13,
																		13))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		loginField,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		88,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		passwordField,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		74,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		appLocationField)
																.addGap(18, 18,
																		18)
																.addComponent(
																		getHotDeployinhButton())
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		getGetForEditButton())
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		getSaveOnServerButton())
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)))));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														getHotDeployinhButton())
												.addComponent(
														getGetForEditButton())
												.addComponent(
														getSaveOnServerButton())
												.addComponent(
														loginField,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														passwordField,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														appLocationField,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(getScriptType(),
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(tableScrollPanel,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										275, Short.MAX_VALUE).addContainerGap()));
	}

	private void setCentered() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		setSize(width / 2, height / 2);

		setLocationRelativeTo(null);
	}

	public static void setLookAndFeel() {
		try {
			UIManager.put("Table.gridColor", Color.LIGHT_GRAY);
			UIManager.put("Table.cellNoFocusBorder", new EmptyBorder(10, 10,
					10, 10));
			UIManager.put("Table.focusCellHighlightBorder", new EmptyBorder(10,
					10, 10, 10));
			UIManager.put("Table.focusSelectedCellHighlightBorder",
					new EmptyBorder(10, 10, 10, 10));
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public javax.swing.JButton getGetForEditButton() {
		return getForEditButton;
	}

	public javax.swing.JButton getSaveOnServerButton() {
		return saveOnServerButton;
	}

	public void setConnectionDataInfoChangeListener(
			final ConnectionDataChangeListener connectionDataInfoChangeListener) {
		this.connectionDataInfoChangeListener = connectionDataInfoChangeListener;

	}

	public javax.swing.JTable getTable() {
		return table;
	}

	public void setTable(final javax.swing.JTable table) {
		this.table = table;
	}

	public List<ScriptDataInfo> getList() {
		return list;
	}

	public List<Long> getSelectedIds() {
		return selectedIds;
	}

	public List<ScriptDataInfo> getSelectedObjects() {
		List<ScriptDataInfo> result = new ArrayList<ScriptDataInfo>();
		for (Long id : selectedIds) {
			for (ScriptDataInfo scriptDataInfo : list) {
				if (id.equals(scriptDataInfo.getId())) {
					result.add(scriptDataInfo);
				}
			}
		}
		return result;
	}

	public void setSelectedIds(final List<Long> selectedIds) {
		this.selectedIds = selectedIds;
	}

	public void fireModelChange(final List<ScriptDataInfo> newModelList) {
		list.clear();
		list.addAll(newModelList);
		((DefaultTableModel) getTable().getModel()).setRowCount(list.size());
		((DefaultTableModel) getTable().getModel()).fireTableDataChanged();

	}

	public String getAllLocation() {
		return appLocationField.getText();
	}

	public javax.swing.JToggleButton getHotDeployinhButton() {
		return hotDeployinhButton;
	}

	public JComboBox getScriptType() {
		return scriptType;
	}

	public void setScriptType(JComboBox scriptType) {
		this.scriptType = scriptType;
	}

	public void setLocalFiles(List<Long> findLocalScripts) {
		localFiles = findLocalScripts;

	}

}
