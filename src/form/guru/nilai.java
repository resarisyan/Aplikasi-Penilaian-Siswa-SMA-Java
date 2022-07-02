package form.guru;

public class nilai {

    private String nis;
    private String nama;
    private float nilaiAbsen;
    private float nilaiTugas;
    private float nilaiUTS;
    private float nilaiUAS;
    private float nilaiAkhir;
    private String predikat;

    public nilai(String nis, String nama, float nilaiAbsen, 
            float nilaiTugas, float nilaiUTS, float nilaiUAS, float nilaiAkhir, String predikat)
    {
        this.nis = nis;
        this.nama = nama;
        this.nilaiAbsen = nilaiAbsen;
        this.nilaiTugas = nilaiTugas;
        this.nilaiUTS = nilaiUTS;
        this.nilaiUAS = nilaiUAS;  
        this.nilaiAkhir = nilaiAkhir;  
        this.predikat = predikat;  
    }
    
    public String getNIS(){
        return nis;
    }
    
    public String getNama(){
        return nama;
    }
    
    public float getNilaiAbsen(){
        return nilaiAbsen;
    }
    
    public float getNilaiTugas(){
        return nilaiTugas;
    }
    
    public float getNilaiUTS(){
        return nilaiUTS;
    }
    
    public float getNilaiUAS(){
        return nilaiUAS;
    }
    
    public float getNilaiAkhir(){
        return nilaiAkhir;
    }
    
    public String getPredikat(){
        return predikat;
    }
    
    
}
