using Script;
using Script.Messages.CsMessages;
using TMPro;
using UnityEngine;
using UnityEngine.SceneManagement;

public class LoginSceneScript : MonoBehaviour
{
    public GameObject userName;

    public GameObject password;

    public GameObject error;

    // Start is called before the first frame update
    void Start()
    {
        error.SetActive(false);
    }

    // Update is called once per frame
    void Update()
    {
        if (UserProperties.MainPlayer.userID == null)
        {
            error.SetActive(UserProperties.LoginFailed);
        }
        else
        {
            SceneManager.LoadScene("AvatarRoomSelectScene");
        }
        
    }

    public void OnTryLogin()
    {
        AppProperties.ServerSession.SendMessage(new CsLogin(userName.GetComponent<TMP_Text>().text, password.GetComponent<TMP_Text>().text));
    }
    
    public void OnSwitchRegister()
    {
        SceneManager.LoadScene("RegisterScene");
    }
}
