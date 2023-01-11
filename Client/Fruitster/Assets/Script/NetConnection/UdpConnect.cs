using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using Script.Messages;
using Script.Messages.ScMessages;
using Script.Utils;
using UnityEngine;

namespace Script
{
    public class UdpConnect
    {
        private bool _stop;
        private IPEndPoint _remoteEndPoint;
        private IPEndPoint _localEndpoint;
        private readonly UdpClient _udpClient;

        public UdpConnect(string ip, int port)
        {
            _remoteEndPoint = new IPEndPoint(IPAddress.Parse(ip), port);
            _udpClient = new UdpClient();
            // _udpClient.Client.SetSocketOption(
            //     SocketOptionLevel.Socket, SocketOptionName.ReuseAddress, true);
            _udpClient.Client.Bind(new IPEndPoint(IPAddress.Any, 56000));
            _localEndpoint = (IPEndPoint) _udpClient.Client.LocalEndPoint;
            _udpClient.Connect(_remoteEndPoint);
            Init();
        }

        private void Init()
        {
            try
            {
                new Thread(Listen).Start();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        public void Send(Message message)
        {
            try
            {
                var sendString = JsonUtils.MessageToJson(message);
                // Translate the passed message into ASCII and store it as a Byte array.
                var data = Encoding.ASCII.GetBytes(sendString);

                // Send the message to the connected TcpServer.
                _udpClient.Send(data, data.Length);
                Debug.Log("Sent:" + sendString);
            }
            catch (Exception e)
            {
                Debug.Log(e);
                Disconnect();
            }
        }

        private void Listen()
        {
            while (AppProperties.IsRunning)
            {
                if (_stop) break;

                if(_udpClient.Available > 0)
                {
                    // Blocks until a message returns on this socket from a remote host.
                    var receiveBytes = _udpClient.Receive(ref _remoteEndPoint);
                    var returnData = Encoding.ASCII.GetString(receiveBytes);
                    foreach (ScMessage receivedMessage in JsonUtils.JsonToMessages(returnData))
                    {
                        SingletonDontDestroy.Instance.DoAction(() => receivedMessage.OnMessage(new Session()));

                    }
                }
            }
        }

        private void Disconnect()
        {
            _stop = true;
            _udpClient.Close();
        }
        
        private void OnApplicationQuit()
        {
            _stop = true;
            _udpClient.Close();
        }
    }
}