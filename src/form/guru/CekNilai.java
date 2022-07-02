/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package form.guru;

import database.KoneksiDB;
import form.DashboardGuru;
import form.Utility;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
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
public class CekNilai extends javax.swing.JFrame {

    /**
     * Creates new form CekNilai
     */
    KoneksiDB k = new KoneksiDB();
    Connection con = k.connection;
    String nip;
    String kode_matkul;
    int id_kelas;
    String namaTabel = "nilai";

    public CekNilai(String nip, int id_kelas, String kode_matkul) {
        setLocationRelativeTo(null);
        initComponents();
        this.nip = nip;
        this.id_kelas = id_kelas;
        this.kode_matkul = kode_matkul;
        show_nilai_inJtable();
        tblNilai.setAutoCreateRowSorter(true);
    }
    
    public void search(String str) {
        DefaultTableModel model = (DefaultTableModel) tblNilai.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        tblNilai.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter("(?i)" + str));
    }
    
    public ArrayList<nilai> getNilaiList() {
        ArrayList<nilai> nilaiList = new ArrayList<nilai>();

        String query = "SELECT n.id_nilai, n.nilai_absen, n.nilai_uts, n.nilai_uas, n.nilai_tugas, d.nama, d.nis FROM "+ namaTabel +" as n RIGHT JOIN datasiswa as d ON n.nis = d.nis WHERE d.id_kelas = "+id_kelas + " AND n.kode_matkul = " + kode_matkul;

        Statement st;
        ResultSet rs;

        try {
            st = con.createStatement();
            rs = st.executeQuery(query);

            nilai Nilai;
            int no = 0;
            float nilai_akhir = 0;
            String predikat="";
            while (rs.next()) {
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
                
                Nilai = new nilai(rs.getString("nis"), rs.getString("nama").toLowerCase(),
                        rs.getFloat("nilai_absen"), rs.getFloat("nilai_tugas"),
                        rs.getFloat("nilai_uts"), rs.getFloat("nilai_uas"), nilai_akhir, predikat);
                nilaiList.add(Nilai);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nilaiList;
    }

    public void show_nilai_inJtable() {

        ArrayList<nilai> list = getNilaiList();

        DefaultTableModel model = (DefaultTableModel) tblNilai.getModel();

        Object[] row = new Object[9];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getNIS();
            row[1] = Utility.capitalizeWord(list.get(i).getNama());
            row[2] = list.get(i).getNilaiAbsen();
            row[3] = list.get(i).getNilaiTugas();
            row[4] = list.get(i).getNilaiUTS();
            row[5] = list.get(i).getNilaiUAS();
            row[6] = list.get(i).getNilaiAkhir();
            row[7] = list.get(i).getPredikat();

            model.addRow(row);
        }
    }
    
    public void showNilai(String squery){
        
        DefaultTableModel model = (DefaultTableModel) tblNilai.getModel();
        
        Statement st;
        ResultSet rs;
        String query = "SELECT n.id_nilai, n.nilai_absen, n.nilai_uts, n.nilai_uas, n.nilai_tugas, d.nama, d.nis FROM "+ namaTabel +" as n RIGHT JOIN datasiswa as d ON n.nis = d.nis WHERE d.id_kelas = "+id_kelas + " AND n.kode_matkul = " + kode_matkul + " AND d.nama LIKE '%"+ squery +"%'";
        String status = null;

        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            
            Object[] row = new Object[9];
            int no = 0;
            float nilai_akhir = 0;
            String predikat="";
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
                row[1] = rs.getString("nis");
                row[2] = rs.getString("nama");
                row[3] = rs.getString("nilai_absen");
                row[4] = rs.getString("nilai_tugas");
                row[5] = rs.getString("nilai_uts");
                row[6] = rs.getString("nilai_uas");
                row[7] = nilai_akhir;
                row[8] = predikat;
                model.addRow(row);
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void printNilai(){
        String query = "SELECT n.nilai_absen, n.nilai_uts, n.nilai_uas, n.nilai_tugas, (( n.nilai_absen+n.nilai_tugas+n.nilai_uts+n.nilai_uas)/4) AS nilaiAkhir, n.catatan, ds.nama as namaSiswa, dg.nama as namaGuru, m.nama_matkul, k.tingkat, k.jurusan, (SELECT nama FROM dataguru WHERE nip = k.wali_kelas) as wali_kelas FROM nilai as n INNER JOIN datasiswa as ds USING (nis) INNER JOIN dataguru as dg USING (nip) INNER JOIN matapelajaran as m USING (kode_matkul) INNER JOIN kelas as k USING (id_kelas) WHERE n.kode_matkul ='"+ kode_matkul +"' AND n.kelas_id="+id_kelas;
        File reportFile = new File(".");
        String dirr = "";
        try {
            Statement stat = con.createStatement();
            dirr = reportFile.getCanonicalPath() + "/src/form/guru/";
            JasperDesign design = JRXmlLoader.load(dirr + "reportNilaiSiswa.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(design);
            ResultSet rs = stat.executeQuery(query);
            JRResultSetDataSource rsDataSource = new JRResultSetDataSource(rs);
            JasperPrint jp = JasperFillManager.fillReport(jr, new HashMap(), rsDataSource);
            JasperViewer.viewReport(jp,false);
        } catch(SQLException | IOException | JRException ex) {
            JOptionPane.showMessageDialog(null, "\nGagal Mencetak\n"+ ex, "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     public void RefreshPage(){
        DefaultTableModel model = (DefaultTableModel)tblNilai.getModel();
        model.setRowCount(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tfSearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNilai = new javax.swing.JTable();
        btnPrint = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setLocationByPlatform(true);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        tfSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSearchKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("CEK NILAI:");

        tblNilai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NIS", "NAMA", "NILAI ABSEN", "NILAI TUGAS", "NILAI UTS", "NILAI UAS", "NILAI AKHIR", "PREDIKAT"
            }
        ));
        tblNilai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.setViewportView(tblNilai);

        btnPrint.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-print.png"))); // NOI18N
        btnPrint.setText("PRINT");
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(113, 113, 113)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(btnPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
             this.dispose();
        }
        String keyword = tfSearch.getText().toLowerCase();
        search(keyword);
    }//GEN-LAST:event_tfSearchKeyReleased

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // TODO add your handling code here:
        printNilai();
    }//GEN-LAST:event_btnPrintActionPerformed

    /**
     * @param args the command line arguments
     */
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrint;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblNilai;
    private javax.swing.JTextField tfSearch;
    // End of variables declaration//GEN-END:variables
}
