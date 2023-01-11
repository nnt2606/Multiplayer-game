using System;
using UnityEngine;

namespace Script
{
    public class AppProperties: MonoBehaviour
    {
        // public static String ServerIp = "127.0.0.1";
        public static String ServerIp = "172.16.194.120";
        // public static String ServerIp = "10.90.61.1";
        public const int ServerTcpPort = 11000;
        public const int ServerUdpPort = 56000;
        
        public static String AppFilePath { get; private set; }

        // private static Session _serverSession;


        public static Session ServerSession { get; set; }

        public static bool IsRunning{ get; private set; }


        private void Awake()
        {
            AppFilePath = Application.dataPath;
            IsRunning = true;
            DontDestroyOnLoad(this);
        }

        private void OnApplicationQuit()
        {
            IsRunning = false;
        }
    }
}