using System;
using System.Net;
using Script.Character;
using Script.Messages.CsMessages;
using TMPro;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

namespace Script
{
    public class LoadingSceneScript : MonoBehaviour
    {
        [SerializeField] private Text input;
        [SerializeField] private TMP_Text output;
        private Session serverSession;
        private void Awake()
        {
            var avatarManager = AvatarSetManager.Instance;
            EnemyManager enemyManager =EnemyManager.Instance;
            ItemManager itemManager = ItemManager.Instance;
            SingletonDontDestroy singletonDontDestroy = SingletonDontDestroy.Instance;
            GameObject appProperties = new GameObject("AppProperties");
            appProperties.AddComponent<AppProperties>();
            output.gameObject.SetActive(false);
            // UdpConnect udpConnect = new UdpConnect(AppProperties.ServerIp, AppProperties.ServerUdpPort);
            // udpConnect.Send(new CsChat("ahihi"));
        }

        private void Update()
        {
            if (serverSession != null && serverSession.IsReady())
            {
                AppProperties.ServerSession = serverSession;
                SceneManager.LoadScene("LoginScene");
            }
        }

        public void Init()
        {
            AppProperties.ServerIp = input.text;
            try
            {
                if (IPAddress.TryParse(AppProperties.ServerIp, out _))
                {
                    serverSession = new Session(AppProperties.ServerIp, AppProperties.ServerTcpPort);
                    output.gameObject.SetActive(true);
                    output.text = "Connecting to the server...";
                }
                else
                {
                    output.gameObject.SetActive(true);
                    output.text = "IP incorrect, please retry...";
                }
            }
            catch (Exception e)
            {
                output.gameObject.SetActive(true);
                output.text = e.Message;
            }
        }
    }
}