using System;
using System.Net;
using Script.Messages.ScMessages;

namespace Script.Messages.CsMessages.GamePlay
{
    [Serializable]
    public class ScGameEnd : ScMessage
    {
        public override void OnMessage(Session session)
        {
            PlaySceneScript.Instance.GameEnd();
        }
    }
}