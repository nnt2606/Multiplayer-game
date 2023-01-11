using System;
using Script.Messages.ScMessages;
using UnityEngine;

namespace Script.NetConnection.Messages.ScMessages.GamePlay
{
    [Serializable]
    public class ScSpawnItem : ScMessage
    {
        public string itemID;
        public string itemName;
        public int positionX;
        public int positionY;
        public override void OnMessage(Session session)
        {
            PlaySceneScript.Instance.SpawnItem(itemID, itemName, new Vector3Int(positionX, positionY, 0));
        }
    }
}