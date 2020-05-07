package me.inao.plugin.thelogin.communication;

import lombok.RequiredArgsConstructor;
import me.inao.plugin.thelogin.Main;
import me.inao.plugin.thelogin.utils.Announce;
import java.net.ServerSocket;

@RequiredArgsConstructor
public class Server extends Thread{
    private final Main main;
    @Override
    public void run(){
        if(main.isSlave()){
            new Announce(main).sayConsole("Server is running in Slave mode. Not turning server on.");
        }else{
            try(ServerSocket socket = new ServerSocket(5544)){
                for(;;){
                    new Connection(main, socket.accept()).start();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                this.interrupt();
            }
        }
    }
}
