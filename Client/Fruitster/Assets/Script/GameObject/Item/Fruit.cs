using Script.Messages.CsMessages.GamePlay;
using Script.Utils;
using UnityEngine;

namespace Script.Item
{
    public class Fruit : MonoBehaviour
    {
        private long latestSentTime;
        public void OnTriggerEnter2D(Collider2D other)
        {
            if (TimeUtils.CurrentTimeMillis() > latestSentTime + GamePlayProperties.TcpInterval)
            {
                latestSentTime = TimeUtils.CurrentTimeMillis();
                AppProperties.ServerSession.SendMessage(new CsUserObtainItem(gameObject.GetComponent<ObjectScript>().id));
            }
        }
    }
}