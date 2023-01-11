using UnityEngine;

public class AvatarSet
{
    public string AvatarName { get; private set;}
    public string AvatarDescription{ get; private set;}
    public GameObject AvatarPrefab{ get; private set;}
    public AvatarSet(string avatarName)
    {
        AvatarName = avatarName;
        AvatarDescription = (Resources.Load("AvatarSet/" + avatarName + "/" + avatarName + "Description") as TextAsset)?.text;
        AvatarPrefab = Resources.Load("AvatarSet/" + avatarName + "/" + avatarName) as GameObject;
    }
}