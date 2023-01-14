using UnityEngine;
using UnityEngine.Tilemaps;

namespace Script.UserInterface
{
    public class TileMapRenderer : MonoBehaviour
    {
        public int tilemapSizeX = 20;
        public int tilemapSizeY = 20;
        
        [SerializeField] private new Camera camera;
        [SerializeField] private Tile land;
        [SerializeField] private Tile border;

        [SerializeField] private Tilemap tilemap;

        
        private void Start()
        {
            for (int x = 0; x < tilemapSizeX + 2; x++)
            {
                for (int y = 0; y < tilemapSizeY + 2; y++)
                {
                    Vector3Int p = new Vector3Int(x,y,0);
                    if (x == 0 || y == 0 || x == tilemapSizeX + 1 || y == tilemapSizeY + 1)
                    {
                        tilemap.SetTile(p, border);
                    }
                    else
                    {
                        tilemap.SetTile(p, land);
                    }
                }
            }

            Vector3 center = tilemap.CellToWorld(new Vector3Int(tilemapSizeX/2+1,tilemapSizeY/2+1,0));

            var transform1 = camera.transform;
            center.z = transform1.position.z;
            transform1.position = center;
        }
    }
}