using System;
using System.Collections.Generic;
using Script.Messages.ScMessages;
using Script.Model;
using UnityEngine.SceneManagement;

namespace Script.Messages.CsMessages.GamePlay
{
    [Serializable]
    public class ScGameStart : ScMessage
    {
        public List<PlayerInitState> playerInitStateList;
        public long gameTime;
        public override void OnMessage(Session session)
        {
            SceneManager.LoadScene("PlayScene");
            SingletonDontDestroy.Instance.DoAction(() =>
            {
                PlaySceneScript.Instance.InitPlayer(playerInitStateList,gameTime);
            });
        }
    }
}