using System;
using Script.Messages.ScMessages;
using UnityEngine;

namespace Script.NetConnection.Messages.ScMessages.GamePlay
{
    [Serializable]
    public class ScSpawnEnemy : ScMessage
    {
        public string enemyID;
        public string enemyName;
        public int positionX;
        public int positionY;
        public override void OnMessage(Session session)
        {
            PlaySceneScript.Instance.SpawnEnemy(enemyID, enemyName, new Vector3Int(positionX, positionY, 0));
        }
    }
}