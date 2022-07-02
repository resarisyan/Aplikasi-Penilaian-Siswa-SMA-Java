/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package form.admin;

import database.KoneksiDB;
import form.DynamicArrayInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author resar
 */

public class Jadwal extends javax.swing.JInternalFrame {

    /**
     * Creates new form Profile
     */
    KoneksiDB k = new KoneksiDB();
    Connection con = k.connection;
    String idJadwal[];
    int indexJadwal;
    String jadwal = "";
    String namaTabel = "jadwal";
    DynamicArrayInteger idKelas = new DynamicArrayInteger(1);
//    int idKelas[] = new int[100];


    public Jadwal() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        showMatkulList();
        showJadwal();
        showGuruList();
        showKelasList();
        tblMapel.setAutoCreateRowSorter(true);
    }
    
    public void showMatkulList(){
        String namaMatkul;
        Statement st;
        ResultSet rs;
        String query = "SELECT * FROM matapelajaran";
        try{
            st = con.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()){
                namaMatkul = rs.getString("kode_matkul") + "-" + rs.getString("nama_matkul");
                cbMatkul.addItem(namaMatkul);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showGuruList(){
        String namaGuru;
        Statement st;
        ResultSet rs;
        String query = "SELECT nip,nama FROM dataguru";
        try{
            st = con.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()){
                namaGuru = rs.getString("nip") + "-" + rs.getString("nama");
                cbPengajar.addItem(namaGuru);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void showKelasList(){
        String namaKelas;
        Statement st;
        ResultSet rs;
        String query = "SELECT * FROM kelas ORDER BY tingkat ASC";
        try{
            st = con.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()){
                idKelas.insert(rs.getInt("id_kelas"));
                namaKelas = rs.getString("tingkat") + " " + rs.getString("jurusan") + "-" + rs.getString("kode_kelas");
                cbKelas.addItem(namaKelas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showJadwal(){
        
        DefaultTableModel model = (DefaultTableModel) tblMapel.getModel();
        
        Statement st;
        ResultSet rs;
        
        String query = "SELECT id_jadwal, m.nama_matkul, m.kode_matkul, k.tingkat, k.jurusan, k.kode_kelas, d.nama, d.nip FROM " + namaTabel  +" as j INNER JOIN matapelajaran as m ON j.kode_matkul = m.kode_matkul INNER JOIN kelas as k ON j.id_kelas = k.id_kelas INNER JOIN dataguru as d ON d.nip = j.nip;";
        String status = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            
            idJadwal = new String [100];
            Object[] row = new Object[6];
            int no = 0;
            while(rs.next()){
                idJadwal[no] = rs.getString("id_jadwal");
                row[0] = ++no;
                row[1] = rs.getString("kode_matkul")+ "-" +rs.getString("nama_matkul");
                row[2] = rs.getString("tingkat") + " " + rs.getString("jurusan") + "-" + rs.getString("kode_kelas");
                row[3] = rs.getString("nama");
                model.addRow(row);
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void executeSQLQuery(String query, String pesan, int check)
    {
        Statement st;
        ResultSet rs;
        String[] nipGuru = cbPengajar.getSelectedItem().toString().split("\\-");
        String[] kodeMatkul = cbMatkul.getSelectedItem().toString().split("\\-");
        
        String queryCheck = "call checkJadwal('"+kodeMatkul[0] +"', '"+nipGuru[0] +"', '"+idKelas.arr[cbKelas.getSelectedIndex()] +"')";
        
        try{
            st = con.createStatement();
            rs = st.executeQuery(queryCheck);
            if(check == 1){
                if(!rs.next()){
                    if(st.executeUpdate(query)>0){
                        RefreshPage();
                        JOptionPane.showMessageDialog(null, "Data " + pesan+ " Succesfully");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Data Not " + pesan);            
                    }
                } else{
                    JOptionPane.showMessageDialog(null, "Jadwal Sudah Terdaftar");
                }
            } else{
                if(st.executeUpdate(query)>0){
                    RefreshPage();
                    JOptionPane.showMessageDialog(null, "Data " + pesan+ " Succesfully");
                } 
                else{
                    JOptionPane.showMessageDialog(null, "Data Not " + pesan);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void RefreshPage(){
        DefaultTableModel model = (DefaultTableModel)tblMapel.getModel();
        model.setRowCount(0);
        showJadwal();
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
        tblMapel = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblMatkul = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        cbMatkul = new javax.swing.JComboBox<>();
        lblKodeKelas = new javax.swing.JLabel();
        cbKelas = new javax.swing.JComboBox<>();
        lblPengajar = new javax.swing.JLabel();
        cbPengajar = new javax.swing.JComboBox<>();

        setFocusCycleRoot(false);
        setFocusable(false);
        setPreferredSize(new java.awt.Dimension(730, 420));
        setRequestFocusEnabled(false);

        tblMapel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO", "MATKUL", "KELAS", "PENGAJAR"
            }
        ));
        tblMapel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tblMapel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMapelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMapel);

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-add.png"))); // NOI18N
        btnAdd.setText("TAMBAH");
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-making_notes.png"))); // NOI18N
        btnEdit.setText("EDIT");
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-delete_trash.png"))); // NOI18N
        btnDelete.setText("DELETE");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        lblMatkul.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMatkul.setText("MATKUL:");

        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-reset.png"))); // NOI18N
        btnReset.setText("RESET");
        btnReset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        lblKodeKelas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblKodeKelas.setText("KELAS:");

        lblPengajar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPengajar.setText("PENGAJAR:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblMatkul)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbMatkul, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblPengajar)
                                .addComponent(lblKodeKelas))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cbKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbPengajar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMatkul)
                            .addComponent(cbMatkul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPengajar)
                            .addComponent(cbPengajar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblKodeKelas)
                            .addComponent(cbKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnEdit))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblMapelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMapelMouseClicked
        DefaultTableModel model = (DefaultTableModel)tblMapel.getModel();
        indexJadwal = tblMapel.getSelectedRow();
        jadwal = (model.getValueAt(indexJadwal, 0).toString());
        Statement st;
        ResultSet rs;
        
        String query = "SELECT id_jadwal, m.kode_matkul, k.kode_kelas, d.nip FROM "+ namaTabel +" as j INNER JOIN matapelajaran as m ON j.kode_matkul = m.kode_matkul INNER JOIN kelas as k ON j.id_kelas = k.id_kelas INNER JOIN dataguru as d ON d.nip = j.nip WHERE id_jadwal ="+idJadwal[indexJadwal];
        try {
            String[] nipGuru;
            String[] kodeMatkul;
            String[] kodeKelas;
            st = con.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()){
                for(int x=0; x<cbKelas.getItemCount(); x++){
                    kodeKelas = cbKelas.getItemAt(x).split("\\-");
                    if(rs.getString("kode_kelas").equals(kodeKelas[1])){
                        cbKelas.setSelectedIndex(x);
                    }
                }
                
                for(int x=0; x<cbMatkul.getItemCount(); x++){
                    kodeMatkul = cbMatkul.getItemAt(x).split("\\-");
                    if(rs.getString("kode_matkul").equals(kodeMatkul[0])){
                        cbMatkul.setSelectedIndex(x);
                    }
                }
                
                for(int x=0; x<cbPengajar.getItemCount(); x++){
                    nipGuru = cbPengajar.getItemAt(x).split("\\-");
                    if(rs.getString("nip").equals(nipGuru[0])){
                        cbPengajar.setSelectedIndex(x);
                    }
                }
                
            }
                        
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_tblMapelMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        if(checkField()){
            String[] nipGuru = cbPengajar.getSelectedItem().toString().split("\\-");
            String[] kodeMatkul = cbMatkul.getSelectedItem().toString().split("\\-");

            String query = "INSERT INTO " + namaTabel +"(`kode_matkul`, `nip`, `id_kelas`)"+
                " VALUES ('"+kodeMatkul[0]+"', '"+nipGuru[0]+"', '"+idKelas.arr[cbKelas.getSelectedIndex()]+"')";
            executeSQLQuery(query, "Inserted", 1);
        } else{
            JOptionPane.showMessageDialog(this, "Isi Semua Field!");
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        if(jadwal.isEmpty()){
            JOptionPane.showMessageDialog(this, "Pilih Data Yang Ingin Di Edit!");
        } else{
            if(checkField()){
                int conf;
                conf = JOptionPane.showConfirmDialog(null, "Apakah Anda Yakin Ingin Mengedit Data Ini?",
                    "Warning",JOptionPane.WARNING_MESSAGE);
                if(conf == 0){
                    String[] nipGuru = cbPengajar.getSelectedItem().toString().split("\\-");
                    String[] kodeMatkul = cbMatkul.getSelectedItem().toString().split("\\-");

                    String query = "UPDATE "+ namaTabel +" SET `kode_matkul` = '" +kodeMatkul[0]+"', "+
                            "`nip` = '"+nipGuru[0]+"', "+
                            "`id_kelas` = '"+idKelas.arr[cbKelas.getSelectedIndex()]+"' "+
                            "WHERE `id_jadwal` ="+idJadwal[indexJadwal];
                    executeSQLQuery(query, "Updated", 1); 
                }
            } else{
                JOptionPane.showMessageDialog(this, "Isi Semua Field!");
            }
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if(jadwal.isEmpty())
         {
             JOptionPane.showMessageDialog(this, "Pilih Data Yang Ingin Di Hapus!");          
         }
         else{
            int conf;
            conf = JOptionPane.showConfirmDialog(null, "Apakah Anda Yakin Ingin Menghapus Data Ini?",
                    "Warning",JOptionPane.WARNING_MESSAGE);
            if(conf == 0){
                String query = "DELETE FROM "+ namaTabel +" WHERE id_jadwal="+idJadwal[indexJadwal];
                executeSQLQuery(query, "Deleted", 3);
            }
         }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        cbMatkul.setSelectedIndex(0);
        cbKelas.setSelectedIndex(0);
        cbPengajar.setSelectedIndex(0);
    }//GEN-LAST:event_btnResetActionPerformed

    public boolean checkField(){
        if(
                cbMatkul.getSelectedItem().toString().trim().isEmpty() ||
                cbPengajar.getSelectedItem().toString().trim().isEmpty() ||
                cbKelas.getSelectedItem().toString().trim().isEmpty()
                ) {
            return false;
        } else{
            return true;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cbKelas;
    private javax.swing.JComboBox<String> cbMatkul;
    private javax.swing.JComboBox<String> cbPengajar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblKodeKelas;
    private javax.swing.JLabel lblMatkul;
    private javax.swing.JLabel lblPengajar;
    private javax.swing.JTable tblMapel;
    // End of variables declaration//GEN-END:variables
}
