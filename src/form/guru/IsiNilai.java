/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package form.guru;

import database.KoneksiDB;
import form.DynamicArrayInteger;
import java.awt.event.KeyEvent;
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

public class IsiNilai extends javax.swing.JInternalFrame {

    /**
     * Creates new form Profile
     */
    KoneksiDB k = new KoneksiDB();
    Connection con = k.connection;
    String nip;
    String nis;
    int idNilai;
    int indexNilai;
    String nilai = "";
    String namaTabel = "nilai";
    DynamicArrayInteger idKelas = new DynamicArrayInteger(1);
    boolean refresh = false;

    public IsiNilai(String nip) {
        this.nip = nip;
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        tblNilai.setAutoCreateRowSorter(true);
        showKelasList();
    }
    
    public void showMatkulList(int id_kelas){
        System.out.println("nip "+nip);
        System.out.println("id_kelas "+id_kelas);
        String namaMatkul;
        Statement st;
        ResultSet rs;
        String query = "SELECT m.kode_matkul, m.nama_matkul FROM matapelajaran as m INNER JOIN jadwal as j ON m.kode_matkul = j.kode_matkul WHERE j.nip = "+ nip +" AND j.id_kelas = "+id_kelas;
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
    
    public void showKelasList(){
        String namaKelas;
        Statement st;
        ResultSet rs;
        String query = "SELECT DISTINCT k.id_kelas, k.tingkat, k.jurusan, k.kode_kelas FROM kelas as k INNER JOIN jadwal as j ON k.id_kelas = j.id_kelas WHERE j.nip = "+ nip  +" ORDER BY k.tingkat ASC";
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
    
    public void showSiswa(int id_kelas){
        
        DefaultTableModel model = (DefaultTableModel) tblNilai.getModel();
        
        Statement st;
        ResultSet rs;
        String[] kodeMatkul = cbMatkul.getSelectedItem().toString().split("\\-");

        String query = "SELECT nama, nis FROM datasiswa WHERE id_kelas = "+id_kelas;
        String status = null;

        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            
            Object[] row = new Object[3];
            int no = 0;
            while(rs.next()){
                row[0] = ++no;
                row[1] = rs.getString("nis");
                row[2] = rs.getString("nama");
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
        String[] kodeMatkul = cbMatkul.getSelectedItem().toString().split("\\-");
        
        String queryCheck = "call checkNilai('"+kodeMatkul[0] +"', '"+ tfNIS.getText() +"')";
        
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
                    JOptionPane.showMessageDialog(null, "Nilai Sudah Terdaftar");
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
        DefaultTableModel model = (DefaultTableModel)tblNilai.getModel();
        model.setRowCount(0);
        showSiswa(idKelas.arr[cbKelas.getSelectedIndex()]);
    }
    
    public void RefreshMatkuList(boolean refresh){
        cbMatkul.removeAllItems();
        showMatkulList(idKelas.arr[cbKelas.getSelectedIndex()]);
    }
    
    public void Reset(){
        tfNIS.setText("");
        tfNama.setText("");
        tfNilaiAbsen.setText("");
        tfNilaiTugas.setText("");
        tfNilaiUAS.setText("");
        tfNilaiUTS.setText("");
        taCatatan.setText("");
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
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblMatkul = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        cbMatkul = new javax.swing.JComboBox<>();
        lblKodeKelas = new javax.swing.JLabel();
        cbKelas = new javax.swing.JComboBox<>();
        lblNIS = new javax.swing.JLabel();
        tfNIS = new javax.swing.JTextField();
        lblNama = new javax.swing.JLabel();
        tfNama = new javax.swing.JTextField();
        lblNilaiAbsen = new javax.swing.JLabel();
        tfNilaiAbsen = new javax.swing.JTextField();
        lblNilaiTugas = new javax.swing.JLabel();
        tfNilaiTugas = new javax.swing.JTextField();
        tfNilaiUTS = new javax.swing.JTextField();
        lblNilaiUTS = new javax.swing.JLabel();
        lblNilaiUAS = new javax.swing.JLabel();
        tfNilaiUAS = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        taCatatan = new javax.swing.JTextArea();
        lblNilaiUAS1 = new javax.swing.JLabel();
        btnCekNilai = new javax.swing.JButton();

        setFocusCycleRoot(false);
        setFocusable(false);
        setPreferredSize(new java.awt.Dimension(740, 470));
        setRequestFocusEnabled(false);

        tblNilai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO", "NIS", "NAMA"
            }
        ));
        tblNilai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tblNilai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNilaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNilai);

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

        cbMatkul.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbMatkulItemStateChanged(evt);
            }
        });

        lblKodeKelas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblKodeKelas.setText("KELAS:");

        cbKelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbKelasItemStateChanged(evt);
            }
        });

        lblNIS.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNIS.setText("NIS:");

        tfNIS.setEditable(false);

        lblNama.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNama.setText("NAMA:");

        tfNama.setEditable(false);

        lblNilaiAbsen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNilaiAbsen.setText("NILAI ABSEN:");

        tfNilaiAbsen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfNilaiAbsenKeyTyped(evt);
            }
        });

        lblNilaiTugas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNilaiTugas.setText("NILAI TUGAS:");

        tfNilaiTugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfNilaiTugasKeyTyped(evt);
            }
        });

        tfNilaiUTS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfNilaiUTSKeyTyped(evt);
            }
        });

        lblNilaiUTS.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNilaiUTS.setText("NILAI UTS:");

        lblNilaiUAS.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNilaiUAS.setText("NILAI UAS:");

        tfNilaiUAS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfNilaiUASKeyTyped(evt);
            }
        });

        taCatatan.setColumns(20);
        taCatatan.setRows(5);
        jScrollPane2.setViewportView(taCatatan);

        lblNilaiUAS1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNilaiUAS1.setText("CATATAN:");

        btnCekNilai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCekNilai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N
        btnCekNilai.setText("CEK NILAI");
        btnCekNilai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCekNilai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekNilaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCekNilai))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblKodeKelas))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMatkul)
                            .addComponent(cbMatkul, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfNIS, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNama)
                            .addComponent(tfNama, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNIS))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfNilaiAbsen, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNilaiTugas)
                            .addComponent(tfNilaiTugas, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNilaiAbsen))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfNilaiUTS, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNilaiUAS)
                            .addComponent(tfNilaiUAS, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNilaiUTS))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNilaiUAS1)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMatkul)
                    .addComponent(lblKodeKelas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbMatkul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNilaiAbsen)
                    .addComponent(lblNilaiUTS)
                    .addComponent(lblNilaiUAS1)
                    .addComponent(lblNIS))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(tfNIS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(13, 13, 13)
                            .addComponent(lblNama)
                            .addGap(7, 7, 7)
                            .addComponent(tfNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(tfNilaiAbsen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(13, 13, 13)
                            .addComponent(lblNilaiTugas)
                            .addGap(7, 7, 7)
                            .addComponent(tfNilaiTugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(tfNilaiUTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(13, 13, 13)
                            .addComponent(lblNilaiUAS)
                            .addGap(7, 7, 7)
                            .addComponent(tfNilaiUAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete)
                    .addComponent(btnReset)
                    .addComponent(btnCekNilai))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblNilaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNilaiMouseClicked
        DefaultTableModel model = (DefaultTableModel)tblNilai.getModel();
        indexNilai = tblNilai.getSelectedRow();
        nilai = (model.getValueAt(indexNilai, 0).toString());
        nis = (model.getValueAt(indexNilai, 1).toString());
        Statement st;
        ResultSet rs;
        String[] kode_matkul = cbMatkul.getSelectedItem().toString().split("\\-");

        
        String query = "SELECT n.id_nilai, n.nilai_absen, n.nilai_uts, n.nilai_uas, n.nilai_tugas, n.catatan, d.nis, d.nama FROM "+ namaTabel +" as n RIGHT JOIN datasiswa as d USING (nis) WHERE n.kelas_id="+idKelas.arr[cbKelas.getSelectedIndex()] + " AND n.kode_matkul ="+kode_matkul[0]+" AND n.nis = " + nis;
        try {
            String[] nipGuru;
            String[] kodeMatkul;
            String[] kodeKelas;
            st = con.createStatement();
            rs = st.executeQuery(query);
            if(rs.next()){
                idNilai = rs.getInt("id_nilai");
                tfNIS.setText(rs.getString("nis"));
                tfNama.setText(rs.getString("nama"));
                tfNilaiAbsen.setText(rs.getString("nilai_absen"));
                tfNilaiTugas.setText(rs.getString("nilai_tugas"));
                tfNilaiUTS.setText(rs.getString("nilai_uts"));
                tfNilaiUAS.setText(rs.getString("nilai_uas"));
                taCatatan.setText(rs.getString("catatan"));
            } else{
                query = "SELECT nis, nama FROM datasiswa WHERE id_kelas="+idKelas.arr[cbKelas.getSelectedIndex()]+" AND nis = " + nis;
                try{
                    st = con.createStatement();
                    rs = st.executeQuery(query);
                    while(rs.next()){
                        idNilai = 0;
                        tfNIS.setText(rs.getString("nis"));
                        tfNama.setText(rs.getString("nama"));
                        tfNilaiAbsen.setText("");
                        tfNilaiTugas.setText("");
                        tfNilaiUTS.setText("");
                        tfNilaiUAS.setText("");
                        taCatatan.setText("");
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
                        
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_tblNilaiMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        if(checkField()){
            String[] kodeMatkul = cbMatkul.getSelectedItem().toString().split("\\-");

            String query = "INSERT INTO " + namaTabel +"(`nilai_absen`, `nilai_uts`, `nilai_uas`, `nilai_tugas`, `catatan`, `nis`, `nip`, `kode_matkul`, `kelas_id`)"+
                " VALUES ('"+tfNilaiAbsen.getText()+"', '"+tfNilaiUTS.getText()+"', '"+tfNilaiUAS.getText()+"', '"+tfNilaiTugas.getText()+"', '"+taCatatan.getText()+"', '"+tfNIS.getText()+"', '"+nip+"','"+kodeMatkul[0]+"','"+idKelas.arr[cbKelas.getSelectedIndex()]+"')";
            executeSQLQuery(query, "Inserted", 1);
        } else{
            JOptionPane.showMessageDialog(this, "Isi Semua Field!");
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        if(nilai.isEmpty()){
            JOptionPane.showMessageDialog(this, "Pilih Data Yang Ingin Di Edit!");
        } else{
            if(idNilai != 0){
                if(checkField()){
                    int conf;
                    conf = JOptionPane.showConfirmDialog(null, "Apakah Anda Yakin Ingin Mengedit Data Ini?",
                    "Warning",JOptionPane.WARNING_MESSAGE);
                    if(conf == 0){
                        String[] kodeMatkul = cbMatkul.getSelectedItem().toString().split("\\-");

                        String query = "UPDATE "+ namaTabel +" SET `nilai_absen` = '" +tfNilaiAbsen.getText()+"', "+
                                "`nilai_uts` = '"+tfNilaiUTS.getText()+"', "+
                                "`nilai_uas` = '"+tfNilaiUAS.getText()+"', "+
                                "`nilai_tugas` = '"+tfNilaiTugas.getText()+"', "+
                                "`catatan` = '"+taCatatan.getText()+"' "+
                                "WHERE `id_nilai` ="+idNilai;

                        executeSQLQuery(query, "Updated", 2); 
                    }
                } else{
                    JOptionPane.showMessageDialog(this, "Isi Semua Field!");
                }
            } else{
                JOptionPane.showMessageDialog(this, "Data Belum Penah Diinputkan!");
            }
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if(nilai.isEmpty())
         {
             JOptionPane.showMessageDialog(this, "Pilih Data Yang Ingin Di Hapus!");          
         }
         else{
            if(idNilai != 0){
                int conf;
                conf = JOptionPane.showConfirmDialog(null, "Apakah Anda Yakin Ingin Menghapus Data Ini?",
                        "Warning",JOptionPane.WARNING_MESSAGE);
                if(conf == 0){
                    String query = "DELETE FROM "+ namaTabel +" WHERE id_nilai="+idNilai;
                    executeSQLQuery(query, "Deleted", 3);
                }
            } else{
                JOptionPane.showMessageDialog(this, "Data Belum Pernah Diinputkan!");          
            }
         }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        Reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void cbKelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbKelasItemStateChanged
        // TODO add your handling code here:
        refresh = true;
        nilai = "";
        Reset();
        RefreshMatkuList(true);
        RefreshPage();
    }//GEN-LAST:event_cbKelasItemStateChanged

    private void cbMatkulItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbMatkulItemStateChanged
        // TODO add your handling code here:
        if(refresh == false){
            RefreshPage();
            Reset();
            nilai = "";
        } else{
            refresh = false;
        }
    }//GEN-LAST:event_cbMatkulItemStateChanged

    private void btnCekNilaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekNilaiActionPerformed
        // TODO add your handling code here:
        String[] kodeMatkul = cbMatkul.getSelectedItem().toString().split("\\-");
        new CekNilai(nip, idKelas.arr[cbKelas.getSelectedIndex()], kodeMatkul[0]).setVisible(true);
    }//GEN-LAST:event_btnCekNilaiActionPerformed

    private void tfNilaiAbsenKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNilaiAbsenKeyTyped
        char c = evt.getKeyChar();
        if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                  evt.consume();
        }
    }//GEN-LAST:event_tfNilaiAbsenKeyTyped

    private void tfNilaiUTSKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNilaiUTSKeyTyped
        char c = evt.getKeyChar();
        if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                  evt.consume();
        }
    }//GEN-LAST:event_tfNilaiUTSKeyTyped

    private void tfNilaiTugasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNilaiTugasKeyTyped
        char c = evt.getKeyChar();
        if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                  evt.consume();
        }
    }//GEN-LAST:event_tfNilaiTugasKeyTyped

    private void tfNilaiUASKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNilaiUASKeyTyped
        char c = evt.getKeyChar();
        if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                  evt.consume();
        }
    }//GEN-LAST:event_tfNilaiUASKeyTyped

    public boolean checkField(){
        if(
                cbMatkul.getSelectedItem().toString().trim().isEmpty() ||
                cbKelas.getSelectedItem().toString().trim().isEmpty() ||
                tfNIS.getText().trim().isEmpty() ||
                tfNama.getText().trim().isEmpty() ||
                tfNilaiAbsen.getText().trim().isEmpty() ||
                tfNilaiTugas.getText().trim().isEmpty() ||
                tfNilaiUAS.getText().trim().isEmpty() ||
                tfNilaiUTS.getText().trim().isEmpty() ||
                taCatatan.getText().trim().isEmpty()
                ) {
            return false;
        } else{
            return true;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCekNilai;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cbKelas;
    private javax.swing.JComboBox<String> cbMatkul;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblKodeKelas;
    private javax.swing.JLabel lblMatkul;
    private javax.swing.JLabel lblNIS;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblNilaiAbsen;
    private javax.swing.JLabel lblNilaiTugas;
    private javax.swing.JLabel lblNilaiUAS;
    private javax.swing.JLabel lblNilaiUAS1;
    private javax.swing.JLabel lblNilaiUTS;
    private javax.swing.JTextArea taCatatan;
    private javax.swing.JTable tblNilai;
    private javax.swing.JTextField tfNIS;
    private javax.swing.JTextField tfNama;
    private javax.swing.JTextField tfNilaiAbsen;
    private javax.swing.JTextField tfNilaiTugas;
    private javax.swing.JTextField tfNilaiUAS;
    private javax.swing.JTextField tfNilaiUTS;
    // End of variables declaration//GEN-END:variables
}
