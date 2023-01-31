using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Fader : MonoBehaviour
{
    // Start is called before the first frame update
    [SerializeField] private float fadeSpeed = 1;
    void Start()
    {
        StartCoroutine(Appear());
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void Die()
    {
        StartCoroutine(Disappear());
    }

    private IEnumerator Appear()
    {
        gameObject.layer = LayerMask.NameToLayer("Init");
        Color startColor = this.GetComponent<SpriteRenderer>().material.color;
        startColor.a = 0;

        this.GetComponent<SpriteRenderer>().material.color = startColor;
        this.GetComponent<Animator>().enabled = false;
        
        while (this.GetComponent<SpriteRenderer>().material.color.a < 1)
        {
            Color objectColor = this.GetComponent<SpriteRenderer>().material.color;
            float fadeAmount = objectColor.a + (fadeSpeed * Time.deltaTime);

            objectColor = new Color(objectColor.r, objectColor.g, objectColor.b, fadeAmount);
            this.GetComponent<SpriteRenderer>().material.color = objectColor;
            yield return null;
        }
        
        this.GetComponent<Animator>().enabled = true;
        gameObject.layer = LayerMask.NameToLayer("Enemy");
    }
    
    private IEnumerator Disappear()
    {
        gameObject.layer = LayerMask.NameToLayer("Init");
        this.GetComponent<Animator>().enabled = false;
        
        while (this.GetComponent<SpriteRenderer>().material.color.a > 0)
        {
            Color objectColor = this.GetComponent<SpriteRenderer>().material.color;
            float fadeAmount = objectColor.a - (fadeSpeed * Time.deltaTime);

            objectColor = new Color(objectColor.r, objectColor.g, objectColor.b, fadeAmount);
            this.GetComponent<SpriteRenderer>().material.color = objectColor;
            yield return null;
        }
        
        Destroy(gameObject);
    }
}
