using System;
using UnityEngine;

namespace Script.Messages.ScMessages
{
    [Serializable]
    public class ScReceiveError : ScMessage
    {
        public string message;
        public override void OnMessage(Session session)
        {
            Debug.Log(message);
        }
    }
}