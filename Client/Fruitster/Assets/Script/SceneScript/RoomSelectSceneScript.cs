using System;
using System.Collections.Generic;
using Script;
using Script.Messages.CsMessages;
using Script.Model;
using UnityEngine;
using UnityEngine.PlayerLoop;
using UnityEngine.UI;

public class RoomSelectSceneScript : SingletonMonoBehavior<RoomSelectSceneScript>
{
    public List<SimpleRoom> rooms = new List<SimpleRoom>();

    [SerializeField] private List<Button> roomButtons;

    [SerializeField] private InputField createRoomName;
    [SerializeField] private InputField createRoomPass;

    [SerializeField] private InputField joinRoomId;

    [SerializeField] private InputField joinRoomPass;

    [SerializeField] private GameObject joinRoomPanel;

    // Start is called before the first frame update
    private void Start()
    {
        Refresh();
    }

    // Update is called once per frame
    private void Update()
    {
        for (var i = 0; i < roomButtons.Count; i++)
            if (i >= rooms.Count)
            {
                roomButtons[i].gameObject.SetActive(false);
            }
            else
            {
                roomButtons[i].gameObject.SetActive(true);
                roomButtons[i].transform.Find("RoomSize").GetComponent<Text>().text =
                    rooms[i].numberOfMembers + "/" + GamePlayProperties.MaxMemberNumber;
                roomButtons[i].transform.Find("RoomName").GetComponent<Text>().text = rooms[i].roomName;

                var i1 = i;
                roomButtons[i].onClick.RemoveAllListeners();
                roomButtons[i].onClick.AddListener(() =>
                {
                    if (!rooms[i1].hasPass)
                    {
                        JoinRoom(rooms[i1].roomID, null);
                    }
                    else
                    {
                        joinRoomPanel.SetActive(true);
                        joinRoomId.text = rooms[i1].roomID;
                    }
                });
            }
    }

    public void Refresh()
    {
        AppProperties.ServerSession.SendMessage(new CsGetRoomList(5));
    }

    public void CreateRoom()
    {
        UserProperties.UserRoom.RoomName = createRoomName.text;

        AppProperties.ServerSession.SendMessage(new CsCreateRoom(createRoomName.text, createRoomPass.text));
    }

    public void JoinRoom()
    {
        UserProperties.UserRoom.RoomID = joinRoomId.text;
        AppProperties.ServerSession.SendMessage(new CsJoinRoom(joinRoomId.text, joinRoomPass.text));
    }

    private void JoinRoom(string roomId, string pass)
    {
        UserProperties.UserRoom.RoomID = roomId;
        AppProperties.ServerSession.SendMessage(new CsJoinRoom(roomId, pass));
    }
}