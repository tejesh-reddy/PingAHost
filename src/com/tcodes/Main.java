package com.tcodes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String addr;
        int port;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter IP: ");
        addr = scanner.next();

        System.out.println("Enter port number: ");
        port = scanner.nextInt();

        System.out.println("Average ping time is: " + getAveragePingTime(addr, port, 11));
    }

    public static double getAveragePingTime(String ipAddr, int port, int reps)
    {
        ArrayList<Long> pingTimes = new ArrayList<>();

        pingTimes = getPingTimes(ipAddr, port, reps);

        return getMedian(pingTimes);
    }

    public static double getMedian(ArrayList<Long> values)
    {
        Collections.sort(values);
        int length = values.size();

        if(length%2 == 1){
            return values.get(length/2+1);
        }
        else {
            Double val = (double) (values.get(length / 2) + values.get(length / 2 + 1)) / 2;
            return val;
        }
    }

    public static ArrayList<Long> getPingTimes(String ipAddr, int port, int reps)
    {
        ArrayList<Long> times = new ArrayList<>();

        for (int i = 0; i < reps; i++) {
            try {
                long responseTime = 0;

                InetAddress inetAddress = InetAddress.getByName(ipAddr);
                InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, port);

                SocketChannel socketChannel = SocketChannel.open();
                socketChannel.configureBlocking(true);

                Date start = new Date();
                if(socketChannel.connect(socketAddress)) {
                    Date stop = new Date();
                    responseTime = (stop.getTime()) - start.getTime();
                    times.add(responseTime);
                }

                socketChannel.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return times;
    }
}
