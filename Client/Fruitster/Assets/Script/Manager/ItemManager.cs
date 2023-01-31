using System.Collections.Generic;
using System.IO;
using System.Text.RegularExpressions;
using UnityEngine;

namespace Script.Character
{
    public class ItemManager
    {
        private static ItemManager _instance;

        public static ItemManager Instance
        {
            get
            {
                if (_instance == null)
                {
                    _instance = new ItemManager();
                    Init();
                }

                return _instance;
            }
        }
        private readonly List<GameObject> enemies = new List<GameObject>();

        private static void Init()
        {
            string[] lines = Regex.Split((Resources.Load("Config/ItemConfig") as TextAsset)?.text!, "\r\n|\r|\n");
            foreach (var line in lines)
            {
                GameObject itemPrefab = Resources.Load("Item/" + line + "/" + line) as GameObject;
                _instance.enemies.Add(itemPrefab);
            }
        }

        public bool HasItem(string enemyName)
        {
            foreach (GameObject enemy in enemies)
            {
                if (enemy.name==enemyName)
                {
                    return true;
                }
            }

            return false;
        }

        public GameObject GetItem(string enemyName)
        {
            foreach (GameObject enemy in enemies)
            {
                if (enemy.name==enemyName)
                {
                    return enemy;
                }
            }

            return null;
        }

        public List<GameObject> Get()
        {
            return new List<GameObject>(enemies);
        }
    }
}