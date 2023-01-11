using System;
using System.Net.Sockets;
using System.Threading;
using Script.Messages.CsMessages;
using Script.Messages.ScMessages;
using Script.Utils;
using UnityEngine;

namespace Script
{
    public class Session
    {
        private String sessionId;
        private TcpClient server;
        private NetworkStream serverStream;
        
        public bool Disconnected { get; private set; }
        // private long lastTimeReceiveMessage;

        public Session()
        {
            
        }
        
        public Session(String ip, int port)
        {
            // Create a TcpClient.
            // Note, for this client to work you need to have a TcpServer
            // connected to the same address as specified by the server, port
            // combination.
            server = new TcpClient(ip, port);
            Connect();
        }
        
        private void Connect()
        {
            try
            {
                Disconnected = false;
                
                // Receive the TcpServer.response.
                new Thread(() =>
                {
                    serverStream = server.GetStream();
                    ListenMessage();
                }).Start();
                SendMessage(new CsInitSession());
            }
            catch (ArgumentNullException e)
            {
                Debug.Log("ArgumentNullException:" + e);
            }
            catch (SocketException e)
            {
                Debug.Log("SocketException: " + e);
            }
        }

        private void ListenMessage()
        {
            while (AppProperties.IsRunning && !Disconnected)
            {
                try
                {
                    if (server.Available > 0)
                    {
                        // Buffer to store the response bytes.
                        Byte[] data = new Byte[2048];
                        // String to store the response ASCII representation.

                        // Read the first batch of the TcpServer response bytes.
                        Int32 bytes = serverStream.Read(data, 0, data.Length);
                        string responseData = System.Text.Encoding.ASCII.GetString(data, 0, bytes);
                        
                        // SendMessage(new Ping());
                        Debug.Log(responseData);
                        try
                        {
                            foreach (ScMessage receivedMessage in JsonUtils.JsonToMessages(responseData))
                            {
                                Debug.Log("Received: " + receivedMessage.GetType().Name + JsonUtility.ToJson(receivedMessage));
                                SingletonDontDestroy.Instance.DoAction(() => receivedMessage.OnMessage(this));
                            }
                        }
                        catch (Exception e)
                        {
                            Debug.Log(e);
                        }
                    }
                }
                catch (Exception)
                {
                    Disconnect();
                }
                
            }
        }

        public void SendMessage(CsMessage message)
        {
            try
            {
                String sendString = JsonUtils.MessageToJson(message);
                // Translate the passed message into ASCII and store it as a Byte array.
                Byte[] data = System.Text.Encoding.ASCII.GetBytes(sendString);

                // Send the message to the connected TcpServer.
                serverStream.Write(data, 0, data.Length);
                serverStream.Flush();
                Debug.Log("Sent:" + sendString);
            }
            catch (Exception e)
            {
                Debug.Log(e);
                Disconnect();
            }
        }

        public void Disconnect()
        {
            Disconnected = true;
            server.Close();
            serverStream.Close();
            OnDisconnect();
        }

        public void OnConnect(String sessionId)
        {
            Debug.Log("Session Connected");
            this.sessionId = sessionId;
            // SendMessage(new CsInitSession());
        }

        public void OnDisconnect()
        {
            Debug.Log("Session Disconnected");
        }

        public bool IsReady()
        {
            return sessionId != null;
        }
    }
}