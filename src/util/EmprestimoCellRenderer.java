/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author FELIPEREMUSDEALMEIDA
 */
public class EmprestimoCellRenderer extends DefaultTableCellRenderer {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final Date hoje = new Date();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        try {
            // Coluna 4 = Status ("Nao Devolvido" ou "Devolvido")
            String status = (String) table.getValueAt(row, 4);
            
            // Coluna 3 = Data de prazo (formato "dd/MM/yyyy")
            String dtPrazoStr = (String) table.getValueAt(row, 3);
            
            if (dtPrazoStr == null || dtPrazoStr.trim().isEmpty()) {
                // Sem data de prazo → mantém cor padrão
                c.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                c.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
                return c;
            }
            
            // Parsear a data de prazo
            Date dtPrazo = sdf.parse(dtPrazoStr);
            
            // Verificar se está atrasado (data atual > data de prazo)
            boolean atrasado = hoje.after(dtPrazo);
            
            // Aplicar cores conforme regra
            if ("Nao Devolvido".equals(status) && atrasado) {
                // Atrasado e ainda não devolvido → VERMELHO
                c.setForeground(Color.RED);
            } else if ("Devolvido".equals(status) && atrasado) {
                // Devolvido, mas com atraso → AMARELO
                c.setForeground(Color.BLUE);
            } else {
                // Normal (dentro do prazo ou devolvido no prazo)
                c.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                c.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
            }
            
        } catch (ParseException e) {
            // Se a data estiver em formato inválido, mantém o padrão
            c.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            c.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
        }
        
        return c;
    }
}