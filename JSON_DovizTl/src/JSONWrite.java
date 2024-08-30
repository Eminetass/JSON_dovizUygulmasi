import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.until.Timer; // Görev metodunu oluştururken kullanacağız.
import java.until.TimerTask;  // Görev methotunu oluştururken kullanacağız


public class JSONWrite {
    static int saatBasi = 0;  // Görev methotu için tanımladık.
    public void baslat(){
        // JSON Dosyasındaki Verileri İnternet Sitesinden Çekerek "veri.JSON" dosyasına yazdıracağız.
        try{
            URL url = new URL("https://finans.truncgil.com/v3/today.json"); //Veriyi alacağımız URL giriyoruz.
            HttpURLConnection hr = (HttpURLConnection) url.openConnection();
            //System.out.println(hr.getResponseCode()); - Bağlantı cevap numarasını öğreniyoruz.
            if(hr.getResponeCode() == 200){  //Bağlantı cevap numaramız bize lazım olan 200 oldugunda çalışır
                InputStream im= hr.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(im));
                //FileOutputStream fileOutputStream = new FileOutputStream(C:\Users\Asus\IdeaProjects\JSON_DovizTl\src\dovizVeri.JSON)
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                String veri = (String) bufferedReader.readline();
                while(veri!=null){
                    bufferedWriter.write(veri);  //Okunan veriyi yazdırıyoruz.
                    bufferedWriter.newLine();   //bir aşağı satıra geçmesini istiyoruz.
                    bufferedWriter.flush();     //Okunan verileri dosyada görmemiz için aktarım işlemini sağlıyor.
                    veri = bufferedReader.readLine();  //Yeni Veri Okutuyoruz.
                }
                //İşlemler bitince Okuma ve Yazma İşlemlerini Kapatıyorum.
                bufferedWriter.close();
                bufferedReader.close();
                im.close();
            }

        }catch (Exception e){
            System.out.println("JSONWrite Sınıfı Exeption Hatası. \n"+e);
        }
    }
    JSONWrite(){
        baslat();   //main sınıfına tanımlama yaptıktan sonra ilk başta dosya oluşmasını istiyorum.
    }


    // JSON Bilgisinin 1 saat boyunca güncellenmesini istiyoruz.
    public void alertTime(){
        Timer alertTime = new Timer();
        TimeTask alertTask = new TimerTask(){
            @Override
            public void run(){
                if(saatBasi < 4){
                    System.out.println(ConsoleManager.RED +"-> Program 15 dakikadır aktif. "+ ConsoleManager.RESET);
                    System.out.println(ConsoleManager.BLUE + "-> JSON Bilgileri Genelde 15 dakikada bir yenilenir. "+ ConsoleManager.RESET);
                    System.out.println(ConsoleManager.YELLOW+ "-> Bu Yüzden JSON Bilgileri Güncellemek için Program Yeniden Başlatıldı. \n"+ ConsoleManager.RESET + BaseMenuCreateManager.menuAyrac);
                    baslat();
                  saatBasi++;
                }else{
                    System.out.println(ConsoleManager.RED+ "-> Program 60 Dakikadır aktif. Program kapatıldı tekrar çalıştırın. \n" + ConsoleManager.RESET+BaseMenuCreateManager.menuAyrac);
                    alertTimer.cancel();
                    System.exit(0);
                }
            }
        };
        alertTimer.schedule(alertTask,900000,900000); //program başladıktan 15dk sonra ilk görev başladıktan sonra her 15dk bir tekrar edicek.
    }


}