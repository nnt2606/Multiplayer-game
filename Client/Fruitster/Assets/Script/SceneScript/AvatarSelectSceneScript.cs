using System.Collections.Generic;
using Script;
using Script.Character;
using Script.Messages.CsMessages;
using UnityEngine;
using UnityEngine.UI;

public class AvatarSelectSceneScript : MonoBehaviour
{
    private static int _userSelection = 0;
    private static List<AvatarSet> _avatarSets;

    [SerializeField] private Text userName;
    
    [SerializeField] private GameObject avatarLocation;
    [SerializeField] private Text avatarLabel;
    [SerializeField] private Text avatarDescription;

    private GameObject actualAvatar;
    // Start is called before the first frame update
    void Start()
    {
        _avatarSets = AvatarSetManager.Instance.Get();

        UserProperties.UserAvatarSet = _avatarSets[0];
        
        UpdateAvatar();
    }

    public void ChooseIncrease()
    {
        _userSelection++;
        if (_userSelection >= _avatarSets.Count)
        {
            _userSelection -= _avatarSets.Count;
        }

        UserProperties.UserAvatarSet = _avatarSets[_userSelection];
        UpdateAvatar();
    }
    
    public void ChooseDecrease()
    {
        _userSelection--;
        if (_userSelection < 0)
        {
            _userSelection = _avatarSets.Count - 1;
        }

        UserProperties.UserAvatarSet = _avatarSets[_userSelection];
        UpdateAvatar();
    }

    private void UpdateAvatar()
    {
        AppProperties.ServerSession.SendMessage(new CsChangeAvatar(UserProperties.UserAvatarSet.AvatarName));
        
        if (actualAvatar != null)
        {
            Destroy(actualAvatar.gameObject);
        }

        actualAvatar = Instantiate(UserProperties.UserAvatarSet.AvatarPrefab,avatarLocation.transform);
        // actualAvatar.transform.Translate(avatar.transform.position);
        avatarLabel.text = UserProperties.UserAvatarSet.AvatarName;
        avatarDescription.text = UserProperties.UserAvatarSet.AvatarDescription;
    }
}
