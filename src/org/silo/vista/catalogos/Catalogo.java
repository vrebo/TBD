package org.silo.vista.catalogos;

import org.silo.vista.catalogos.forms.Form;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import org.jdesktop.swingx.JXTable;
import org.silo.vista.componentes.MyTableModel;
import org.silo.vista.componentes.SearchToolBar;

public abstract class Catalogo extends JPanel {

    protected SearchToolBar searchToolBar;
    protected JPanel form;
    protected TablaPanel panelTabla;
    private JXTable tabla;
    protected final MyTableModel modelo;
    private TableRowSorter<MyTableModel> sorter;
    private boolean editando;

    public Catalogo(JPanel form, MyTableModel modelo) {
        this.form = form;
        this.modelo = modelo;
        setLayout(new BorderLayout());
        initComponents();
        installListeners();
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    private void initComponents() {
        panelTabla = new TablaPanel(modelo);
        sorter = new TableRowSorter<>(modelo);
        tabla = panelTabla.getjXTable();
        tabla.setRowSorter(sorter);
        searchToolBar = new SearchToolBar();

        add(searchToolBar, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(panelTabla, BorderLayout.SOUTH);
    }

    private void newFilter() {
        System.out.println("Buscando por... " + searchToolBar.getSearchField().getText());
        RowFilter<MyTableModel, Object> rf;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter("(?i)" + searchToolBar.getSearchField().getText());
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

    private void installListeners() {
        searchToolBar.getSearchField().addActionListener((ActionEvent e) -> {
            newFilter();
        });
        searchToolBar.getNewEntity().addActionListener((ActionEvent e) -> {
            setEditando(false);
            ((Form) form).cleanData();
        });

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    System.out.println(
                            sorter.convertRowIndexToModel(target.getSelectedRow()));
                    int row = sorter.convertRowIndexToModel(target.getSelectedRow());
                    if (row > -1) {
                        editando = true;
                        ((Form) form).setData(modelo.getData().get(row));
                        System.out.println(modelo.getData().get(row));
                    }
                    System.out.println("click en tabla");
                }
            }
        });
    }
}
