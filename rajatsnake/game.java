/*<applet code="game" width=500 height=500>
</applet>*/
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
public class game extends JApplet implements Runnable,KeyListener,ActionListener{
private AudioClip acsnake,hit;
private static volatile int endcons=0;
private static volatile boolean ispaused=false;
private static volatile boolean lockpanel=false;
private static volatile boolean lockpanel2=false;
private static volatile int flagpause=0;
private static volatile boolean endgame=false;
private static volatile boolean running=false;
private static volatile int dirflag=0;
private static volatile int headx=250;
private static volatile int heady=500;
private static volatile int cookiex;
private static volatile int cookiey;
private static volatile int width=500;
private static volatile int height=500;
private static volatile int cellx[];
private static volatile int celly[];
private static volatile int growflag;
private static volatile int somex,somey;
private static final int maxlen=1000;
public static volatile int time=0;
private static volatile int period=0;
private static volatile int comptime=0;
private volatile int fps=100;
public static int score=0; 
private static volatile int buttonlock;
private static volatile long startime,endtime,sleeptime;
Graphics g,gd;
Image img,nouse,head,cook;
Thread t;
game gl;
private static int flag=0;
private int kcode;
public game(){
cellx=new int[1000];
celly=new int[1000];
growflag=0;
period=1000/fps;
//gl=new game();
//head=getImage(getDocumentBase(),"snake.gif");
setLayout(new FlowLayout());
t=new Thread(this);
Timer t=new Timer(5000,this);
t.start();
//addMouseListener(this);
addKeyListener(this);
setFocusable(true);
requestFocus();
//setBackground(Color.yellow);
//setVisible(true);
}
//
//
//
public void init(){
acsnake=getAudioClip(getDocumentBase(),"rajatmedia/EatIt.au");
hit=getAudioClip(getDocumentBase(),"rajatmedia/hit.au");
head=getImage(getDocumentBase(),"rajatmedia/snake.gif");
cook=getImage(getDocumentBase(),"rajatmedia/goody.gif");
game p=new game();
getContentPane().add(p);
//p.setVisible(true);
p.time=300;
}
//
//
//
public void actionPerformed(ActionEvent ae){
cookiex=(int)(Math.random()*width-10);
cookiey=(int)(Math.random()*height-10);
if(!ispaused&&time!=0){
comptime+=5;
}
}
//
//
//
public void addNotify(){
super.addNotify();
}
//
//
//
public void run(){
//code for running animation
System.out.println("run() is working");
running=true;
while(running){
startime=System.currentTimeMillis();
render();
update();
sleep();
endtime=System.currentTimeMillis()-startime;
//System.out.println(endtime);
//sleeptime=endtime-startime;
sleeptime=period-endtime;
System.out.println("Time taken is: "+sleeptime);
try{
if(sleeptime<=0){Thread.sleep(5);
System.out.println("Time taken is: "+sleeptime);
System.out.println("Inside methods");}
if(sleeptime>0)
{Thread.sleep(sleeptime);}}catch(InterruptedException e){System.out.println("Error");}
}
}
//
//
//
public void keyTyped(KeyEvent ke){
}
//
//
//
public void keyPressed(KeyEvent ke){
if(lockpanel==false){
kcode=ke.getKeyCode();
if(kcode==ke.VK_ENTER)
if(flag==0){
{
t.start();
flag=1;
paintscreen("game started");}
}
if(!lockpanel2){
if(kcode==ke.VK_UP){
if(buttonlock!=3&&dirflag!=4){
//code to move up
//System.out.println("Up received");
dirflag=3;
buttonlock=3;
//System.out.println("dirflag:"+dirflag);
update();
}
}
if(kcode==ke.VK_DOWN){
if(buttonlock!=4&&dirflag!=3){
//code to move down
//System.out.println("Down received");
dirflag=4;
buttonlock=4;
//System.out.println("dirflag:"+dirflag);
update();
}
}
if(kcode==ke.VK_LEFT){
if(buttonlock!=1&&dirflag!=2){
//code to move left
//System.out.println("Left received");
dirflag=1;
buttonlock=1;
//System.out.println("dirflag:"+dirflag);
update();
}
}
if(kcode==ke.VK_RIGHT){
if(buttonlock!=2&&dirflag!=1){
//code to move right
//System.out.println("Right received");
dirflag=2;
buttonlock=2;
//System.out.println("dirflag:"+dirflag);
update();
}
}
}
if(kcode==ke.VK_ESCAPE){
System.out.println("Escaped Received");
if(endcons==1){
endcons=0;
}
switch(flagpause){
case 0:
{
//ispaused=true;
//paintscreen("game paused");
flagpause=1;
stopgame();
break;
}
case 1:
{
//ispaused=false;
//paintscreen("game resumed");
flagpause=0;
resumegame();
break;
}
}
}
if(kcode==ke.VK_0){
endcons+=1;
ispaused=true;
paintscreen("Are you sure?.Press zero to end game.To resume press escape key");
if(endcons==2){
ispaused=true;
endgame=true;
System.out.println("Game Ended");
paintscreen("Game Ended.Your Score is: "+score);
lockpanel=true;
}
}
}
}
//
//
//
public void render(){}
//
//
//
public void resumegame(){
System.out.println("resumegame called");
ispaused=false;
lockpanel2=false;
}
//
//
//
public void stopgame(){
System.out.println("stopgame called");
ispaused=true;
lockpanel2=true;
}
//
//
//
public void update(){
if(!ispaused){
if(dirflag==1){
if(headx==0||headx>0){
if(headx==0){
headx=500;}
paintsnake(headx,heady);
headx-=5;
for(int i=1;i<=growflag;i++){
		   if(headx==cellx[i] && heady==celly[i]){
			//game is over
hit.play();
			ispaused=true;
}
}   

}
}
if(dirflag==2){
if(headx<500||headx==500){
if(headx==500){
headx=0;}
paintsnake(headx,heady);
headx+=5;
for(int i=1;i<=growflag;i++){
		   if(headx==cellx[i] && heady==celly[i]){
			//game is over
hit.play();
			ispaused=true;
}
}   
}
}
if(dirflag==3){
if(heady==0||heady>0){
if(heady==0){
heady=500;}
paintsnake(headx,heady);
heady-=5;
for(int i=1;i<=growflag;i++){
		   if(headx==cellx[i] && heady==celly[i]){
			//game is over
hit.play();
			ispaused=true;
}
}   

}
}
if(dirflag==4){
if(heady<500||heady==500){
if(heady==500){
heady=0;}
paintsnake(headx,heady);
heady+=5;
for(int i=1;i<=growflag;i++){
		   if(headx==cellx[i] && heady==celly[i]){
			//game is over
hit.play();
			ispaused=true;
}
}   

}
}
}
}
//
//
//
public void paintsnake(int x,int y){
//System.out.println("Inside paintsnake");
try{
t.sleep(1);}catch(InterruptedException e){}
img=createImage(width,height);
gd=img.getGraphics();
gd.setColor(Color.yellow);
if(cookiex-5<headx&&headx<cookiex+15){
if(cookiey-5<heady&&heady<cookiey+15){
acsnake.play();
score+=20;
growflag+=1;
cookiex=(int)(Math.random()*width);
cookiey=(int)(Math.random()*height);
cellx[growflag]=headx;
celly[growflag]=heady;
}
}
gd.fillRect(0,0,width,height);
gd.setColor(Color.black);
gd.drawString("Your current score is: "+score,50,20);
gd.drawString("Time Left: "+(time-comptime)+" secs",270,20);
gd.setColor(Color.black);
gd.drawImage(head,headx,heady,null);
gd.setColor(Color.blue);
for(int i=2;i<=growflag;i+=2){
gd.fillOval(cellx[i],celly[i],13,13);
}
cellx[0]=headx;
celly[0]=heady;
for(int i=growflag+1;i>=1;i--){
cellx[i]=cellx[i-1];
celly[i]=celly[i-1];
}
gd.setColor(Color.red);
gd.drawImage(cook,cookiex,cookiey,null);
gd.setColor(Color.blue);
g=this.getGraphics();
g.drawImage(img,0,0,null);
if(comptime==time){
System.out.println("your score is: "+score);
ispaused=true;
paintscreen("Game Over,Your Score is: "+score);
lockpanel=true;
time=0;
}
}
//
//
//
public void sleep(){
try{
t.sleep(0);
}catch(InterruptedException e){
System.out.println("Your game encountered an error");
}
}
//
//
//
public void keyReleased(KeyEvent ke){}
//
//
//
public void paintscreen(String str){
g=this.getGraphics();
img=createImage(width,height);
gd=img.getGraphics();
gd.setColor(Color.yellow);
gd.fillRect(0,0,500,500);
gd.setColor(Color.black);
gd.drawString(str,50,250);
g.drawImage(img,0,0,null);
} 
}
