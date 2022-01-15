import java.io.*;
import java.io.IOException;
import java.util.*;


public class applrun{
    public static void main(String []args) throws IOException {
        Random rand = new Random();
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the hash table dor which the demo should run");
        System.out.println("1.CountMin\n2.Counter Sketch\n3.Active Counter");
        int option=sc.nextInt();
        FileWriter output = new FileWriter("output_proj3_" + option + ".txt");
        File file = new File("project3input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int n = Integer.parseInt(br.readLine());
        ArrayList<Flowparameters> inparr = new ArrayList<Flowparameters>();
        String st;
        while ((st = br.readLine()) != null)
        {
            String ipsize[] = st.split("\\s+");
            inparr.add(new Flowparameters(ipsize[0], Integer.parseInt(ipsize[1]),0));
        }
        if(option==1){
            System.out.println("Enter the number of counter arrays k");
            int k=sc.nextInt();
            System.out.println("Enter the number of counters in each array");
            int w=sc.nextInt();

            CountMin cm=new CountMin(k,w);
            for(Flowparameters p : inparr)
            {
                String flowstr = p.ip;
                flowstr = flowstr.replace(".", "");
                long flow = Long.parseLong(flowstr);
                cm.record(flow,p.orgsize);
            }
            int avgerror=0;
            for(Flowparameters p : inparr)
            {
                String flowstr = p.ip;
                flowstr = flowstr.replace(".", "");
                long flow = Long.parseLong(flowstr);
                int size=p.orgsize;
                int countminoutput=cm.lookup(flow,size);
                p.rcrdsize=countminoutput;
                avgerror += (countminoutput - size);
            }
            System.out.println("Avg error among all flows:"+avgerror/n);
            output.write("Avg error among all flows:"+avgerror/n+"\n");
            Collections.sort(inparr);
            Flowparameters l;
            for (int j=0; j<100; j++) {
                l = inparr.get(j);
                System.out.println(l.ip + " " + l.rcrdsize + " " + l.orgsize);
                output.write(l.ip + " " + l.rcrdsize + " " + l.orgsize + "\n");
            }
            output.close();
            }

        else if (option==2) {
            System.out.println("Enter the number of counter arrays k");
            int k=sc.nextInt();
            System.out.println("Enter the number of counters in each array");
            int w=sc.nextInt();
        CounterSketch cm=new CounterSketch(k,w);
        for(Flowparameters p : inparr)
        {
            String flowstr = p.ip;
            flowstr = flowstr.replace(".", "");
            int flow1=flowstr.hashCode();

//            long flow = Long.parseLong(flowstr);
            cm.record(flow1,p.orgsize);
        }
            int avgerror=0;
            for(Flowparameters p : inparr)
            {
                String flowstr = p.ip;
                flowstr = flowstr.replace(".", "");
                int flow1=flowstr.hashCode();
//                long flow = Long.parseLong(flowstr);
                int size=p.orgsize;
                int countminoutput=cm.lookup(flow1,size);
                p.rcrdsize=countminoutput;
                avgerror += Math.abs(countminoutput - size);
            }
            System.out.println("Avg error among all flows:"+avgerror/n);
            output.write("Avg error among all flows:"+avgerror/n+"\n");
            Collections.sort(inparr);
            Flowparameters l;
            for (int j=0; j<100; j++) {
                l = inparr.get(j);
                System.out.println(l.ip + " " + l.rcrdsize + " " + l.orgsize);
                output.write(l.ip + " " + l.rcrdsize + " " + l.orgsize + "\n");
            }
            output.close();

        }

        else if (option==3) {
            System.out.println("Enter the number of number of times the active counter to be increased");
            int nt=sc.nextInt();

                System.out.println("Enter the number of number bits");
                int num=sc.nextInt();
                System.out.println("Enter the number of exponent bits");
                int exp=sc.nextInt();
            ActiveCounter ac=new ActiveCounter(num,exp);
            int res=ac.Counterprocessing(nt);
            System.out.println(res);
            output.write(String.valueOf(res));
            output.close();

        }

    }
}
class Flowparameters implements Comparable<Flowparameters> {
    String ip;
    Integer orgsize;
    double rcrdsize;

    Flowparameters() {

    }

    Flowparameters(String ip, int orgsize, int rcrdsize) {
        this.ip = ip;
        this.orgsize = orgsize;
        this.rcrdsize = rcrdsize;
    }

    public void setRcrdsize(Integer rcrdsize) {
        this.rcrdsize = rcrdsize;
    }

    public int getRcrdsize() {
        return rcrdsize;
    }
    @Override
    public int compareTo(Flowparameters fp) {
        int comparercrd = ((Flowparameters) fp).getRcrdsize();
        return  comparercrd-this.rcrdsize;
    }
}
class CountMin{
    int k;
    int w;
    int []s;
    int [][] counterarr;
    Random rand=new Random();
    CountMin(int k, int w){
        this.k=k;
        this.w=w;
        this.s=new int[k];
        this.counterarr=new int[k][w];
        for(int i=0;i<k;i++){
            Arrays.fill(counterarr[i],0);
        }
        hashfunctions();
    }
    public void record(long flow,int size){
        for(int i=0;i<k;i++){
            int xor=(int)((flow^s[i])%w);
            counterarr[i][xor]+=size;
        }
    }
    public int lookup(long flow,int size){
        int min=Integer.MAX_VALUE;;
        for(int i=0;i<k;i++){
            int xor=(int)((flow^s[i])%w);
            if(min > counterarr[i][xor] )
                min = counterarr[i][xor] ;
        }

        return min;
    }
    public void hashfunctions(){
        for(int i=0;i<this.k;i++){
            s[i]=Math.abs(rand.nextInt());
        }
    }
}
class CounterSketch{
    int k;
    int w;
    int []s;
    int [][] counterarr;
    Random rand=new Random();
    CounterSketch(int k, int w){
        this.k=k;
        this.w=w;
        this.s=new int[k];
        this.counterarr=new int[k][w];
        for(int i=0;i<k;i++){
            Arrays.fill(counterarr[i],0);
        }
        hashfunctions();
    }
    public void record(int flow,int size){
        int sign;
        int temp;
        for(int i=0;i<k;i++){
            temp=(int)(flow^s[i]);
            sign=(temp >> 31) & 1;
            int xor=Math.abs(temp%w);
            counterarr[i][xor]+=((sign == 1)? size : -(size));
        }
    }
    public int lookup(int flow,int size){
        int sign;
        int temp;
        int []median=new int[k];
        for(int i=0;i<k;i++){
            temp=(int)(flow^s[i]);
            sign=(temp >> 31) & 1;
            int xor=Math.abs(temp%w);
            median[i]=((sign == 1)? counterarr[i][xor] : -(counterarr[i][xor]));
        }
        Arrays.sort(median);
        return median[(0+k)/2];
    }
    public void hashfunctions(){
        for(int i=0;i<this.k;i++){
            s[i]=Math.abs(rand.nextInt());
        }
    }
}
class ActiveCounter{
    int numbits;
    int expbits;
    Random random=new Random();

    ActiveCounter(int num,int exp){
        this.numbits=num;
        this.expbits=exp;
    }
    public int Counterprocessing(int n){
        int currnumbits = 0;
        int currexpbits = 0;
        int nbOverflow= (int)Math.pow(2,numbits)-1;
        int expbOverflow= (int)Math.pow(2,expbits)-1;

        for (int i = 0; i < n; i++) {
            if(random.nextInt(((int)Math.ceil(Math.pow(2, currexpbits))))==0){
                if(currnumbits==nbOverflow){
                    currnumbits= currnumbits>>1;
                    currexpbits++;
                    if(currexpbits==expbOverflow){
                        System.out.println("Overflow exponent bit");
                        System.exit(1);
                    }
                }
                else currnumbits++;
            }
        } return (int)(currnumbits*Math.pow(2,currexpbits));

    }}




