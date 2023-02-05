using UnityEngine;
using UnityEngine.Serialization;

namespace Script.Character
{
    public class PlayerAnimator : MonoBehaviour
    {

        [FormerlySerializedAs("MovingAnimation")] public RuntimeAnimatorController movingAnimation;
        [FormerlySerializedAs("IdleAnimation")] public RuntimeAnimatorController idleAnimation;
        [FormerlySerializedAs("HitAnimation")] public RuntimeAnimatorController hitAnimation;

        private Animator characterAnimator;

        private ObjectScript objectScript;
        // Start is called before the first frame update
        void Start()
        {
            characterAnimator = GetComponent<Animator>();
            objectScript = GetComponent<ObjectScript>();
            characterAnimator.runtimeAnimatorController = idleAnimation;
        }

        // Update is called once per frame
        void Update()
        {
            switch (objectScript.state)
            {
                case ObjectState.Chatting:
                case ObjectState.Idle:
                    characterAnimator.runtimeAnimatorController = idleAnimation;
                    break;
                case ObjectState.Moving:
                    characterAnimator.runtimeAnimatorController = movingAnimation;
                    break;
                case ObjectState.Hitted:
                    characterAnimator.runtimeAnimatorController = hitAnimation;
                    break;
            }
        }
    }
}
