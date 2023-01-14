using System;
using System.Collections.Generic;
using System.Runtime.CompilerServices;
using UnityEngine;

namespace Script
{
    public class SingletonDontDestroy : SingletonMonoBehavior<SingletonDontDestroy>
    {
        protected override void Awake()
        {
            base.Awake();
            DontDestroyOnLoad(this);
        }

        private List<Action> waitingAction = new List<Action>();

        public void DoAction(Action action)
        {
            Instance.waitingAction.Add(action);
        }

        private void Update()
        {
            try
            {
                foreach (var action in new List<Action>(waitingAction))
                {
                    try
                    {
                        action.Invoke();
                        waitingAction.Remove(action);
                    }
                    catch (Exception e)
                    {
                        Debug.Log(e);
                    }
                }
            } catch (Exception e)
            {
                Debug.Log(e);
            }
        }
    }
}