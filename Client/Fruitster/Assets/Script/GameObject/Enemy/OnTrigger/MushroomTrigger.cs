using UnityEngine;

namespace Script.Character.Enemy.OnTrigger
{
    public class MushroomTrigger : MonoBehaviour
    {
        void OnTriggerEnter2D(Collider2D other)
        {
            Debug.Log("Mushroom Caught :" + other.gameObject.name);
            ObjectScript @object = other.GetComponent<ObjectScript>();
            other.GetComponent<SpriteRenderer>().material.color = GamePlayProperties.PoisonedColor;
            @object.Speed /= GamePlayProperties.MushroomSlowDownConst;
            
            SingletonDontDestroy.Instance.RunAfterSec(() =>
            {
                other.GetComponent<SpriteRenderer>().material.color = new Color32(255, 255, 255, 255);
                @object.Speed *= GamePlayProperties.MushroomSlowDownConst;
            }, GamePlayProperties.MushroomPoisonTime);
            
            // GetComponent<Fader>()?.Die();
        }

    }
}