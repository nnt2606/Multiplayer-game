using System.Collections;
using System.Collections.Generic;
using Script.Utils;
using UnityEngine;

public class ScreenBouncing : MonoBehaviour
{
    private bool bounce = false;
    // Start is called before the first frame update
    void Start()
    {
    }

    // Update is called once per frame
    void Update()
    {
        // if (!bounce)
        // {
        //     bounce = true;
        //     GetComponent<Rigidbody2D>().AddForce(new Vector2(RandomUtils.NextFloat()*100, 0), ForceMode2D.Impulse);
        //
        // }
    }
    
    void FixedUpdate()
    {
        if (Input. GetKeyDown(KeyCode. Space))
        {
            //Apply a force to this Rigidbody in direction of this GameObjects up axis
            GetComponent<Rigidbody2D>().AddForce(new Vector2(RandomUtils.NextFloat()*100, 0), ForceMode2D.Impulse);
        }
    }
}
