
package xe.ui;


import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;



/**
 *
 * @author BND6699
 */
public class MenuBC extends javax.swing.JFrame {

    /**
     * Creates new form MenuBC
     */
    public MenuBC() {
        initComponents();     

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        ngaybatdau = new com.toedter.calendar.JDateChooser();
        ngaykethuc = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btn_doanhso = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        cb_DSB = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();

        setTitle("Báo cáo");
        setResizable(false);

        ngaybatdau.setDateFormatString("dd/MM/yyyy");

        ngaykethuc.setDateFormatString("dd/MM/yyyy");

        jLabel1.setText("Từ ngày");

        jLabel2.setText("Đến ngày");

        btn_doanhso.setText("Xem");
        btn_doanhso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_doanhsoActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Chọn"));

        buttonGroup1.add(cb_DSB);
        cb_DSB.setText("Danh số bán");
        cb_DSB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_DSBActionPerformed(evt);
            }
        });

        jRadioButton1.setText("số lượng xe");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cb_DSB)
                    .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cb_DSB)
                .addGap(12, 12, 12)
                .addComponent(jRadioButton1)
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("thống kê");
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ngaybatdau, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                    .addComponent(btn_doanhso)
                    .addComponent(ngaykethuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel3)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ngaybatdau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ngaykethuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_doanhso, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_doanhsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_doanhsoActionPerformed
        
        Date startDate = ngaybatdau.getDate();
        Date endDate = ngaykethuc.getDate();
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");                     
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        
  
        if (startDate == null | endDate == null) 
        {
            JOptionPane.showMessageDialog(null, "Hãy chọn ngày bắt đầu và kết thúc");
        }
        
        else if(cb_DSB.isSelected() == true) //Xem doanh số
        {
            String strngaybd = formatter.format(startDate);
            String strngaytk = formatter.format(endDate);
            
            String tungay = formatter2.format(startDate);
            String denngay = formatter2.format(endDate);
            
            Baocao baocao = new Baocao();
            baocao.showDataList(strngaybd, strngaytk);
            baocao.showTong(strngaybd, strngaytk);
            baocao.setNgay(tungay, denngay);
//            
            //vi tri giua man hinh va maximize
            baocao.pack();
            baocao.setLocationRelativeTo(null);
            baocao.setVisible(true);
        }   
        
//        else if(cb_CTDSB.isSelected() == true)// Xem chi tiết doanh số              
//        {
//            String strngaybd = formatter.format(startDate);
//            String strngaytk = formatter.format(endDate);
//            
//            String tungay = formatter2.format(startDate);
//            String denngay = formatter2.format(endDate);
//            
//            CT_Baocao ctbaocao = new CT_Baocao();
//            ctbaocao.showDataList(strngaybd, strngaytk);
//            ctbaocao.showTong(strngaybd, strngaytk);
//            ctbaocao.showGiam(strngaybd, strngaytk);
//            ctbaocao.setNgay(tungay, denngay);
//            
//            //vi tri giua man hinh va maximize
//            ctbaocao.pack();
//            ctbaocao.setLocationRelativeTo(null);
//            ctbaocao.setVisible(true);
//        }
//        else
//        {
//           JOptionPane.showMessageDialog(null, "Hãy chọn 1 trong 2 cái để xem");
//        }     
    }//GEN-LAST:event_btn_doanhsoActionPerformed
    
    //Xuất thống kê sản phẩm
    private void cb_DSBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_DSBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_DSBActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuBC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuBC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuBC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuBC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuBC().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_doanhso;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton cb_DSB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private com.toedter.calendar.JDateChooser ngaybatdau;
    private com.toedter.calendar.JDateChooser ngaykethuc;
    // End of variables declaration//GEN-END:variables
}
