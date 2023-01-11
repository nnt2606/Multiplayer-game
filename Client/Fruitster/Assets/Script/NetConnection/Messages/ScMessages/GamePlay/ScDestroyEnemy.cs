using System;
using Script.Messages.ScMessages;

namespace Script.NetConnection.Messages.ScMessages.GamePlay
{
    [Serializable]
    public class ScDestroyEnemy:ScMessage
    {
        public string enemyID;
        public override void OnMessage(Session session)
        {
            PlaySceneScript.Instance.DestroyById(enemyID);
        }
    }
}