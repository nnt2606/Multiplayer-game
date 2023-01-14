using System;
using System.Collections;
using UnityEngine;

namespace Script
{
    public class SingletonMonoBehavior<T>: MonoBehaviour where T : MonoBehaviour
    {
        private static T _instance;
        public static T Instance
        {
            get
            {
                if (_instance == null)
                {
                    GetInstance();
                }
                return _instance;
            }
        }

        protected virtual void Awake()
        {
            if (_instance == null)
            {
                GetInstance();
                // DontDestroyOnLoad(gameObject);
            }
            // else
            //     Destroy(gameObject);
        }

        private static void GetInstance()
        {
            _instance = (T)FindObjectOfType(typeof(T));
            if (_instance == null)
            {
                var newGo = new GameObject().AddComponent<T>();
                _instance = newGo.GetComponent<T>();
            }
        }

        public void RunAfterSec(Action action, int sec)
        {
            Debug.Log("Start");
            Instance.StartCoroutine(InvokeAfterMillisSec(action, sec));
        }
        
        private IEnumerator InvokeAfterMillisSec(Action action, int sec)
        {
            yield return new WaitForSeconds(sec);
            action?.Invoke();
            Debug.Log("done");
        }
    }
}