/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package form.siswa;

import database.KoneksiDB;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author resar
 */
public class CekNilai extends javax.swing.JInternalFrame {

    /**
     * Creates new form Profile
     */
    KoneksiDB k = new KoneksiDB();
    Connection con = k.connection;
    String nis;
    int idKelas;
    public CekNilai(String nis) {
        this.nis = nis;
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        CekNilaiSiswa();
        getIdKelas();
    }
    
    public void getIdKelas(){
        Statement st;
        ResultSet rs;

        String query ="SELECT id_kelas FROM datasiswa where nis = '"+nis+"'";
        try{
            st = con.createStatement();
            rs = st.executeQuery(query);
            if(rs.next()) idKelas=rs.getInt("id_kelas");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void CekNilaiSiswa(){
        DefaultTableModel model = (DefaultTableModel) tblNilai.getModel();
        
        Statement st;
        ResultSet rs;

        String query = "SELECT m.nama_matkul, d.nama, n.nilai_absen, n.nilai_tugas, n.nilai_uts, n.nilai_uas, n.catatan FROM nilai as n INNER JOIN matapelajaran as m ON m.kode_matkul = n.kode_matkul INNER JOIN dataguru as d ON d.nip = n.nip WHERE n.nis = '"+nis+"'";
        float nilai_akhir;
        String predikat="";

        try {
            float absen = 0, tugas=0, uts=0, uas=0, akhir=0;
            st = con.createStatement();
            rs = st.executeQuery(query);
            
            Object[] row = new Object[10];
            int no = 0;
            while(rs.next()){
                nilai_akhir = ((rs.getFloat("nilai_absen") + rs.getFloat("nilai_tugas") + rs.getFloat("nilai_uts") + rs.getFloat("nilai_uas"))/4);
                if(nilai_akhir >= 85){
                    predikat = "A";
                } else if(nilai_akhir >= 75 && nilai_akhir < 85){
                    predikat = "B+";
                } else if(nilai_akhir >= 70 && nilai_akhir < 75){
                    predikat = "B";
                } else if(nilai_akhir >= 65 && nilai_akhir < 70){
                    predikat = "C+";
                } else if(nilai_akhir >= 60 && nilai_akhir < 65){
                    predikat = "C";
                } else if(nilai_akhir >= 55 && nilai_akhir < 60){
                    predikat = "D+";
                } else if(nilai_akhir >= 50 && nilai_akhir < 55){
                    predikat = "D";
                } else if(nilai_akhir >= 0 && nilai_akhir < 50){
                    predikat = "E";
                }
                row[0] = ++no;
                row[1] = rs.getString("nama_matkul");
                row[2] = rs.getString("nama");
                row[3] = rs.getString("nilai_absen");
                row[4] = rs.getString("nilai_tugas");
                row[5] = rs.getString("nilai_uts");
                row[6] = rs.getString("nilai_uas");
                row[7] = nilai_akhir;
                row[8] = predikat;
                row[9] = rs.getString("catatan");
                model.addRow(row);
                
                absen +=rs.getFloat("nilai_absen"); 
                tugas +=rs.getFloat("nilai_tugas"); 
                uts +=rs.getFloat("nilai_uts"); 
                uas +=rs.getFloat("nilai_uas"); 
                akhir +=nilai_akhir; 
            }
            absen = absen/no;
            tugas = tugas/no;
            uts = uts/no;
            uas = uas/no;
            akhir = akhir/no;
            tfAbsen.setText(Float.toString(absen));
            tfTugas.setText(Float.toString(tugas));
            tfUTS.setText(Float.toString(uts));
            tfUAS.setText(Float.toString(uas));
            tfAkhir.setText(Float.toString(akhir));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblNilai = new javax.swing.JTable();
        btnPrint = new javax.swing.JButton();
        tfAbsen = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfTugas = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfUAS = new javax.swing.JTextField();
        tfUTS = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfAkhir = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(560, 460));

        tblNilai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO", "MATAPELAJARAN", "PENGAJAR", "NILAI ABSEN", "NILAI TUGAS", "NILAI UTS", "NILAI UAS", "NILAI AKHIR", "PREDIKAT", "CATATAN"
            }
        ));
        tblNilai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.setViewportView(tblNilai);

        btnPrint.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-print.png"))); // NOI18N
        btnPrint.setText("PRINT");
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        tfAbsen.setEditable(false);
        tfAbsen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("NILAI RATA-RATA");

        jLabel2.setText("TUGAS:");

        tfTugas.setEditable(false);
        tfTugas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel3.setText("UTS:");

        jLabel4.setText("UAS:");

        tfUAS.setEditable(false);
        tfUAS.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        tfUTS.setEditable(false);
        tfUTS.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tfUTS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfUTSActionPerformed(evt);
            }
        });

        jLabel5.setText("ABSEN:");

        tfAkhir.setEditable(false);
        tfAkhir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tfAkhir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfAkhirActionPerformed(evt);
            }
        });

        jLabel6.setText("AKHIR:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPrint)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfAbsen, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfTugas, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tfUTS, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tfUAS, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnPrint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tfAbsen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(tfUTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(tfAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfUAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfTugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfUTSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfUTSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfUTSActionPerformed

    private void tfAkhirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfAkhirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfAkhirActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        String query = "SELECT m.nama_matkul, n.nilai_absen, n.nilai_tugas, n.nilai_uts, n.nilai_uas, (( n.nilai_absen+n.nilai_tugas+n.nilai_uts+n.nilai_uas)/4) AS nilaiAkhir, n.catatan, "
                + "ds.nama as namaSiswa, ds.nis, k.tingkat, k.jurusan, k.kode_kelas, (SELECT nama FROM dataguru WHERE nip = k.wali_kelas) as wali_kelas "
                + "FROM nilai as n INNER JOIN matapelajaran as m USING(kode_matkul) "
                + "INNER JOIN datasiswa as ds USING(nis) "
                + "INNER JOIN kelas as k USING(id_kelas) "
                + "WHERE n.nis ='"+ nis +"' AND n.kelas_id ="+idKelas;
        File reportFile = new File(".");
        String dirr = "";
        try {
            Statement stat = con.createStatement();
            dirr = reportFile.getCanonicalPath() + "/src/form/siswa/";
            JasperDesign design = JRXmlLoader.load(dirr + "reportNilai.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(design);
            ResultSet rs = stat.executeQuery(query);
            JRResultSetDataSource rsDataSource = new JRResultSetDataSource(rs);
            JasperPrint jp = JasperFillManager.fillReport(jr, new HashMap(), rsDataSource);
            JasperViewer.viewReport(jp, false);
        } catch(SQLException | IOException | JRException ex) {
            JOptionPane.showMessageDialog(null, "\nGagal Mencetak\n"+ ex, "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrint;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblNilai;
    private javax.swing.JTextField tfAbsen;
    private javax.swing.JTextField tfAkhir;
    private javax.swing.JTextField tfTugas;
    private javax.swing.JTextField tfUAS;
    private javax.swing.JTextField tfUTS;
    // End of variables declaration//GEN-END:variables
}
