

package stringplay;
import java.awt.Color;
import javax.swing.event.DocumentEvent;
import org.jdesktop.application.Action;
import org.jdesktop.application.TaskMonitor;
import org.jdesktop.application.SingleFrameApplication;
import java.util.ArrayList;
import java.util.Random;
import org.jdesktop.application.Task;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.DocumentListener;
import org.jdesktop.application.Application;
import javax.swing.filechooser.FileFilter;
import java.io.*;
public class modulus extends javax.swing.JFrame {
    private Color bg=new Color(255,255,255);
    private Color fg=new Color(0,0,0);
    private String all="";
    static SingleFrameApplication app;
        final char[] alphanumerics={'0','1','2','3','4','5','6','7','8','9',
    'q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k',
    'l','z','x','c','v','b','n','m','-','=','[',']',';','\'',',','.','/','`','Q','W','E',
    'R','T','Y','U','I','O','P','A','S','D','F','G','H','J','K',
    'L','Z','X','C','V','B','N','M','_','+','{','}',':','|','<','>','?','~','!','@',
    '#','$','%','^','&','*','(',')','\\','"',' '};
    final String[] key1={"q>(JB,x^","!JT(HTFJ","eCeVP-Iv","vYr2hQ%)","7IWdOieg","_7.:=>KB","5MRBg:/I","X'6^,AH#","Irg,98`0","_.|tA<LV","0#>h1Pi{",";>+WCg[t","WFh6&-k]","5+.zUR3w","ngame8+L","BC}wue^x","6!sn=G6i","L^2tx7k=","Wv,w[lF(","QyU|SGcG","ZGz.O_VI","E3,;}SUk","6'i|Lx@1","teLFB{fK","I8D#5zf:","j2Cy|$ci","0|YzIk~+","xmk&DrS#","T|ZR0jNQ","5agYJ}cK","#1%#:|6'","xnV4wQ)}","3Rs%HeJd","Ds6v8oU;","^byrkM5d","zq4l#k+3","G,5^!Oi$","v~!NSdX#","?G2YVTk-","yf>!s#`|","WdA;'/z<","LE|j.K2s","4/m0$s!.","(BR_tGS`","bypqS7<M","RtN[:P)1","DGs3Zs4x","f)H8430X","E2nUjx2%","AK@U<CZa","7]iH44lI","?W9;1i{S","&);RYj@C","|<rY.brv","mcp-rh}Q","b3k}2aA,",":Qfp!2:M","NzgyJvBy","SQkXdha}","i1](F`Ru","bF'd{jN(","doG1(9}1","r*052yM/","$M]ClTHe","J$3?YCMa","CW'Y+C]7","8*0>blL>",";W~A<trJ","0jLOS:r;","[<%Y[N0~","%viCk}AQ","kyW@>[_i","ja('AD8@","<l0-*c~W","!fvqA0Si","9gxN_1(!","SSCaAGQa","^9C<xJ10","?2L2/?Nh","m<9vXLKE","_Mey=]|U","{h<1erSh","6l]Uyxa6","'1Kb>~;i",":>+(p+4y","KU7bbdmD","sFkR5KBa","JZ3(lM_@","Iq6',|Y-","'%AN?Iqo","3qse%dUJ","x*[IX]mX","qNdet?yA","'O{+*RQQ","8e9iq)1i","J4kl6gS0"};
    final String[] key2={"W2vFl?/A","T9?r,fXK","?1'62ZPW","ZJRucz=C","wpf4OmI^","M,L{WhfP","Mx^6#E6C","hsi]:55t","6B$;$(Dg","Y+bF~u~I","cDOgV?oo","YL.gelN+","/8NPA-2)","l+5vRP?%","IdzqybFr","?.DMCAQs","K8Z(nBH>","oVBQrY<d","sG<DclHk","MtA#%g|R","Vi./=J)5","?84^J<I8","NH)k~/h'","rr.kcHHT","xfsbA^S?","kqg$vC&h","V0=BHqz]","XCpV^z4:","X2vboF6*","xxCfv&#=",";7G?MSa5","Bd%UyAsy","W[PUr!2v","ov6H89;F","xr>/Viw{",")-qq+$-N","kJhnvL2#",">#s46(A:","r+L$!WVo","}}=nwE{<","{?=:y[9<","mqkd-#_/",",i9/JODp","I~1#wj<d","hXAr<v7n","hd'1&m5%","?WFaI}hW","3WD47N?y","Y[OV=iuL","]P8paJs>","/uiR`z!K","{ro%ie19","5xOYgi'>","RFItD$W7","be'W/lyN","E7mw314M","Wm[t75+F",";87b/Glb","8pS{&'wB","I+{iy$BO","UR/C,:vt","_gR9gE2N","&h({@C^G","9mMT7%lT","0-~yIJ)X","a'!?Ri#=","%d??yPHJ","dznH6[{X","#ugyU#R@","*4W8jN[0","bjt}<#_:",".,!jP~hF","auPTPH2M","DH/y9F'i","NbQ<^nE%","tg#$_J4*",".o:-{JjL","K_0W1-]v","?%xlinrm","c~4pAZG_","DhUR'x%N","d=*kNPx%","#Fwd(pve","73nJ>c>f","U:m9_*s}","-&hMV'd@","oj`+'(gI","d0S&5c#O","2uEU):]H","F=ZeD-T^","r$khsxt9","3*)g,6pw","^{Zp]fR&","WFvENF%*","i(tW/vAb","5Gho9j4M"};
    final String[] key3={"U(fd8<D;","*{rO7!'f","T'F$W%g5","V~$vS*H-","L}Naq=!A","pjX'FV%&","7UUPU:G^","UTr]`4xX","Fr7k<a@n","`hccAR&U","0oFvz;nf","R(eZyQOt",")A~kZOM1","n$SX:YMI","=qulfbJ`","c26ok~zB","wY%udYy<",")N&8ahk.","3{2Mb>8)","O[,GV,P,","g3{J@w#M","(+tY4FU*","+]X`+n:k","7%*!v,lT","-a{S2.l7","*2[aVx[C","U#WDsm$$","i1[^1E&?","Ti~rV~lf","cI!CK-I%","%K(rqVBl","~e('>4P&","TM-Zb@5%","i~DVo2b*","5hN0.p}L","B[4N#n`w","S<pYh/Hb","TP>pz+qA","Eh_'%mKX",")iIVJO%A","=+B$)L,3","qnZE//+a","Vg3Nhl!H","[2G5Ns%g","rHFZ&K^0","HU@o{)V$","?2EB+n!,","Me%(_'xN","(haRa~fi","vOXkHOu+",";DQP%|#&","v`w7lU/J","n!uXwqe+","jF=BPX~X","UiOlUJa@","wji?6|1=","E;S-~]a4","c~s(r+0?","$]#Uje2L","g<PZsgHj","G7;f9kS}",".`s;2'yT","!c.7QnTq","R@f%T~z{","qj-3vo{q","}jNV:=Y0","FXMit2a[",";Oi{H5Y-","}n<e!E^;","%Byj9957","&AIJ[0Ip","4!o]bN?L","Q!*b.zW~","#Ob[t'wT","dL1|A6.+","qbX}?Q57","V.ml!~L$","q-ylFsfq","#>%vO=w+","S#V_U8UL",")Rl-G9S1","}*J;ch}o","Oh~`z%dX","|j~4J:)g","~a=AR@(Z","c2BOGq~q","z5$!s_Xi","XEve+Tu!","[mBrZBWA","2xSq_FWc","A#w9XK*+","l2n;mory","?!'P:%-x","S]!.egF8","}`JfYY'[","3B6vJ7cF"};
    final String[] keyN1={"87053836","61434036","30853056","39424185","85796332","20276389","98441065","70923944","09673458","76349409","51204906","25098569","33052694","30031470","89629767","88709762","52964044","32070147","63134754","60960364","42792409","12949132","57106420","70429081","45072509","34617699","23030365","57719571","33696247","28784364","68658025","05546860","02167672","50035279","72119772","84613602","31182363","44220132","97389882","85797804","45402103","91999976","34016331","34146936","64873106","12474985","19226518","13629792","24543713","27169797","26563381","99678378","49509384","52614651","89767398","53787664","45056949","72152532","35213669","78924893","93243049","48352948","46364684","07121263","34006795","42421306","02076884","05278385","04249563","91941542","10091157","21068952","58755452","44048546","72454869","62082909","19049790","50117493","54480046","84715088","00571146","41609081","95912188","39561288","45111694","31226539","77076776","67186677","40750925","08952194","00796584","72525330","47349720","13248324","73051068","05552014"};
    final String[] keyN2={"55062342","89500923","81582876","22904568","64288378","73043994","38037674","74222308","24323414","05729286","44093139","88617941","78140192","65553425","76374447","91872987","21377119","92603907","34495057","83719270","47667059","87160228","06220340","96370592","66289857","10601444","72400876","45583885","37989133","61433480","25842197","07924111","55879867","75066554","36965002","72268424","79707830","87121015","70254442","09949630","43837504","61844069","40345307","25301616","57970464","34571637","94329665","46734488","38802524","64070123","82564188","15786725","39519452","06459674","04950097","36824964","14669032","47377517","81620090","75185493","43701109","19588788","40588642","60853984","57253249","78027848","16847873","11768081","57309541","15187088","81030644","14514805","97839488","45737628","89822735","62444535","18619286","74165888","63194170","36082100","05331597","79315328","78332761","10751313","98763437","21864254","33449940","54823206","39992028","92181733","12193997","28058253","33814314","76241800","33646464","80633182"};
    final String[] keyN3={"03379092","20063210","64234568","27419601","90552974","87186445","52671341","34014848","82032349","20817926","23494090","10787617","10594632","53439350","98529830","51075165","70657142","74384223","53422885","31547743","58966882","76588204","28460362","35663079","79780627","23777747","05963597","14794324","88240098","35711971","98306584","52399306","43692657","92987175","57608760","15705236","64159878","02501956","26365111","80603063","80068184","57372483","89228701","61879580","15400483","69251565","66987379","95626569","09492106","56548851","01504392","90557695","17012157","16984240","62404434","20519739","06225079","83717157","90028567","10669740","24664930","75601401","35148754","81167650","15778563","97732453","53802861","17282494","73866241","21514803","73246098","02523336","61249449","23222330","58688254","33071319","46091476","42612560","54474485","12725002","10047903","23009838","63775980","61517759","99626755","44832644","85057871","21874646","36213923","63664851","25311900","95672262","52805080","02958042","88480702","53075625"};
    final String[] keyA1={"nIHjRzXK","CbqjEUku","zeQrJSmv","AYWxcvKP","QdcxHMBM","ODjfgpUJ","RHZyiRSZ","xdpXAwFj","cEzUoneT","VORMCtlp","rqzopFEG","dQVkUEYs","WvOxOULK","kvcvLKWj","SwnHPXYi","vnYxQZyv","KRaslxqR","ystHUsQy","oKkEaqmz","flIRachW","wwElVjgO","nbqcxcnX","uzwEpnUz","yppZMouS","AYsyaVgX","OIYubCkO","VPUOcIuN","QoErXeDd","eiCbZnqF","MajCeXwf","KrlZujdv","oYxATMYq","urySsosr","YpnHpQLK","SDossTXO","zWUjtrdH","LybeUtkE","JrQxPDyo","MocTdZdi","hsqWKaus","tBsiFKAx","LMDzqVkB","uTwXNXBM","GoSnstys","OJcnwFtX","Imosphjx","LYUAIwJL","OpNYQjpx","XAzwmuiN","mJGMDgws","sLWxEgFn","msCeJVJB","GtsZjRhG","tSryNYaR","knSlYGtp","amZMCXbk","UHmcwfmp","ahtCBnyl","GMOyhBte","KiiWOMHB","eUFGfdZL","zKGHqOFs","zNFvqLws","flSIlhVu","NEMMnizt","UamjCBVD","mAFUSnZZ","EwCQGiCH","oHqJwuzs","sXfjkRbc","ofuEYFma","xEJDAZAL","LtQSGyoU","IvASMQuT","CutDkMGE","XtdjLScB","WdlKuziq","WFjetxbP","cPNgVBnO","USqfYHKC","rNFSzlxp","YYzGpMzK","AKYMYRYq","dJikYtuJ","IkyeQyNd","aeAQpicN","KuLwsWgJ","LPWCkJIu","ZfnYFKru","RKxpRWPp","OAWJCbkU","cdhongJx","xFmjsupQ","ONZZmkMN","juJUEybC","AQzMqQsr"};
    final String[] keyA2={"rPDFbppO","DeflPYVv","EskbTbyl","KzujDJXj","cCiXYwOz","etXEOgEo","xDkeDQjD","rRUPhKlN","nNaDnPYh","UXPyvbUD","AyLRAHGv","YxhFXQxZ","fMNKnoIV","rikpgfkD","edTNnRFe","htbsGsfG","hJsNnVuW","eYDOKOCe","ErAYycby","EZDWSwXj","cvaltJKA","JJvTyeTh","RKSYGuOm","msuxtSfk","ySJbsQLW","nLPUhONn","pORTqFTE","QDPBiTON","gTVBeHGM","HyEHQfhU","jawIiZcW","KXxOiEzS","wpKykCmv","WHTGkopQ","QHIPKtcH","GskPFRFU","qajlzivv","EtKSQPAr","EUMdAKbd","eSzSpuQL","kRTRfuah","YQsGFfiK","SAHkWlzJ","bAfrvliI","eJynehii","tKLSAuAO","oiCnVXyt","HosSkOAv","RZlRirrV","AWZpvWSM","KPoMzPUH","PHDKzcFk","cQVFGNrC","BJPhRUxr","gOAJCVJF","BPlOAyZq","zIzsHNMV","kBEVRLWQ","igEMZeOn","MiwSsQFY","caxqyeir","XgLbZxhV","FQrqALMR","SzXZfSbq","GcMvZnso","OXZVuztf","iIudmlWl","AnuAeRuu","SXKBGthO","nXgYCZeG","OmjVjQGi","uFWAyMUd","bJjNsbzT","yLdVRVZv","WSfhVIiW","RDoUYbtF","viQzQfrx","PpkGdRcW","jcQfCeVW","neJRnAGs","vXDCfyeP","VDSNnKIQ","BUNXaPzz","hodprshC","FbLCKhgm","lRizjVPy","UxONLEVi","zvFuwrjE","XFCRxAcV","WtYfStya","itkDbgrr","lDNVDoRC","cGiVXdty","afDgJLBu","LqYZxUtu","JefymhVc"};
    final String[] keyA3={"mUnYtatu","LxYCxGmf","XhvmbQNE","qxHSBwCy","cGGqcmCf","gTTdlxth","rvlXGjrM","peuzeEXL","znuVPOhb","YcssWPjy","GkXAIHXE","sbjBrPWQ","owBpYcQW","jfseORni","TZITowQH","LnXBShWE","yXZxLjBa","ydLiCLGU","OmbVAZED","FmtBiCap","xHXuNfhY","aoCGzQeV","vWLautdj","FeahvUbN","eUUfehpP","KOczfuUL","iUoxnDyF","paxuyBsu","zAhsrCzc","ElHXNdrG","ZnoQTnoJ","aXlHLeDX","zSnDOPdO","HUPAyRQh","bbTUhmca","EOBlUzMy","ZPXxQykp","SsKqCoQf","MExvWRbJ","GQCnEdrS","iZGdMAQb","ToIVWZzj","jmHHYLKe","gTlaBlBD","EEnHumTq","DWNifVqy","BUybOzZA","JmRXJVOI","WVgIhzvr","KddIDvQu","xajRoBLH","joLrCPXT","XmGsnekV","CXZJpOfW","PaPWuTcL","PMqpkfZV","PeYDFlSj","bWtZWZqb","rDGcKWtu","ITKoqquh","YfoqEELP","hcBLikVM","cdfjnunN","fUMqvdvE","DjuGuPDV","PSANRdci","FmmTjJem","JrrISsGF","GKwgmiSW","raIKNBQe","gqLnAqpa","mfiiSFmz","RtITgIlX","KgyPHHAE","aheYJDrb","ivhNHWmZ","ZIvqqWEe","hYYaiXrT","eVWwBvcj","JoPeRbhV","LAZVhFBf","lvlUhaTW","ExdqaMgl","KHFYlVnp","JPVQVfXP","TCUkkIQE","rMADAaUk","lrjmhSEC","TJRlrrsh","BIxDvBep","NcepAHjA","hASnLasz","uWMjahYe","mOFmIAzm","MIqbGzfY","kVwdDoGA"};
    private String encrypted;
    private String a;

    public modulus(SingleFrameApplication parent, Color a, Color b, String text, int xy) {
        app=parent;
        all=text;
        bg=a;
        fg=b;
        initComponents();
        setVisible(true);
        jTextArea1.setText(""+all);
        updateCount();
        encrypted="";
        String[] key=new String[96];
        Random gen = new Random();
        int x=gen.nextInt(3);
        if (x==0)
            encrypted=encrypted+"0";
        if (x==1)
            encrypted=encrypted+"1";
        if (x==2)
            encrypted=encrypted+"2";
        String[] lines=all.split("\n");
            encrypted=x+"";
        ArrayList<String> enc=new ArrayList<String>();
        if (xy==1){
            if (x==0)
                key=keyN1;
            if (x==1)
                key=keyN2;
            if (x==2)
                key=keyN3;
        }
        if (xy==2){
            if (x==0)
                key=keyA1;
            if (x==1)
                key=keyA2;
            if (x==2)
                key=keyA3;
        }
        if (xy==3){
            if (x==0)
                key=key1;
            if (x==1)
                key=key2;
            if (x==2)
                key=key3;
        }
            for (int y=0;y<lines.length;y++){
                char[] chars=lines[y].toCharArray();
                for (int z=0;z<chars.length;z++){
                    for (int q=0;q<95;q++){
                    if (chars[z]==alphanumerics[q]){
                        enc.add(key[q]);
                        System.out.print(key[q]);
                    }
                }
            }
                enc.add(key[95]);
                System.out.print(key[95]);
                int c=((100*(y+1))/lines.length);
                System.out.println(c+"%");
        }
            encrypted=jTextArea2.getText();
        if (xy==1)
            encrypted=encrypted+"N#";
        if (xy==2)
            encrypted=encrypted+"A@";
        if (xy==3)
            encrypted=encrypted+"A*N";
    }

    public void updateCount(){
        int count=jTextArea1.getText().length();
        String[] sentence=jTextArea1.getText().split("\n");
        String[] word=jTextArea1.getText().split(" ");
        int words=word.length+sentence.length-1;
        jLabel1.setText("Characters: "+count+" Sentences: "+sentence.length+" Words: "+words);
    }

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();

        setTitle("StringPlay - Settings");
        setAlwaysOnTop(true);

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextArea1KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setEditable(false);
        jTextArea2.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jTextArea2);

        jLabel1.setText("Characters");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jProgressBar1.setStringPainted(true);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jProgressBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jProgressBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
            .add(jPanel1Layout.createSequentialGroup()
                .add(jLabel1)
                .addContainerGap())
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextArea1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea1KeyTyped
        updateCount();        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea1KeyTyped
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
    }

    @Action
    public void uppercase() {
        jTextArea1.setText(jTextArea1.getText().toUpperCase());
    }

    @Action
    public void lowercase() {
        jTextArea1.setText(jTextArea1.getText().toLowerCase());
        
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
    
}
