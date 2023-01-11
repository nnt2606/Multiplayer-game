using System;
using System.Collections;
using System.Collections.Generic;
using Cinemachine;
using Script;
using Script.Character;
using Script.Model;
using Script.Utils;
using TMPro;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.Tilemaps;
using UnityEngine.UI;
using Random = Unity.Mathematics.Random;

public class PlaySceneScript : SingletonMonoBehavior<PlaySceneScript>
{
	public const float ImageRaiseSpeed = 0.5f;
	
	[SerializeField] private List<GameObject> userStatistic;
	[SerializeField] private TMP_Text timer;
	[SerializeField] private GameObject timeUpBanner;
	[SerializeField] private Tilemap tilemap;
	[SerializeField] private GameObject finalStatisticPanel;
	[SerializeField] private List<GameObject> playerFinalStatistic;
	

	private new CinemachineVirtualCamera camera;
	private readonly Dictionary<string, GameObject> idToGameObject = new Dictionary<string, GameObject>();

	private readonly Dictionary<string, (int, GameObject)> playerIdToPlayerScore = new Dictionary<string, (int, GameObject)>();
	
	private void Start()
	{
		camera = GameObject.Find( "CM vcam1" ).GetComponent<CinemachineVirtualCamera>();
		
		foreach (GameObject statistic in userStatistic)
		{
			statistic.SetActive( false );
		}
		for( int i = 0; i < UserProperties.UserRoom.Players?.Count; i++ )
		{
			playerIdToPlayerScore.Add( UserProperties.UserRoom.Players[i].userID, ( 0, userStatistic[i] ) );
			userStatistic[i].transform.Find( "UserName" ).GetComponent<TMP_Text>().text = UserProperties.UserRoom.Players[i].userName + " :";
			userStatistic[i].transform.Find( "UserScore" ).GetComponent<TMP_Text>().text = 0 + "";
			userStatistic[i].SetActive( true );
		}
	}

	// Update is called once per frame
	private void FixedUpdate()
	{
		float time = float.Parse(timer.text);
		if (time <= 0 && !timeUpBanner.activeSelf)
		{
			try
			{
				timeUpBanner.SetActive(true);
				timer.text = "0.00";
				Time.timeScale = 0;

				return;
			}
			catch (Exception)
			{
				return;
			}
		}
		if (time < 5)
		{
			timer.color = Color.red;
		}
		
		timer.text = $"{time - Time.deltaTime:F2}";

	}

	public void AddScore( string userID, int score )
	{
		if( playerIdToPlayerScore.ContainsKey( userID ) )
		{
			score += playerIdToPlayerScore[userID].Item1;
			GameObject scoreText = playerIdToPlayerScore[userID].Item2;
			scoreText.transform.Find( "UserScore" ).GetComponent<TMP_Text>().text = score + "";
			playerIdToPlayerScore[userID] = ( score, scoreText );
		}
	}

	public void InitPlayer( List<PlayerInitState> playerInitStateList , long gameTime)
	{
		foreach (PlayerInitState playerInitState in playerInitStateList)
		{
			foreach (User player in UserProperties.UserRoom.Players)
			{
				if( player.userID == playerInitState.id )
				{
					GameObject prefab = AvatarSetManager.Instance.GetAsset(player.avatar).AvatarPrefab;
					Vector3 postion = tilemap.CellToWorld(new Vector3Int(playerInitState.locationX, playerInitState.locationY,0));
					GameObject instantiate = Instantiate( prefab, postion , Quaternion.identity );

					if( instantiate.GetComponent<ObjectScript>() == null )
					{
						instantiate.AddComponent<ObjectScript>();
					}
					instantiate.GetComponent<ObjectScript>().id = player.userID;

					idToGameObject.Add( player.userID, instantiate );
					if (player.userID == UserProperties.MainPlayer.userID)
					{
						camera.Follow = instantiate.transform;
						instantiate.AddComponent<PlayerInputMoving>();
					}
					else
					{
						instantiate.AddComponent<ObjectUpdateMoving>();
					}
					break;
				}
			}
		}
		timer.text = $"{gameTime:F2}";
	}

	public void SpawnEnemy( string id, string type, Vector3Int position)
	{
		if(EnemyManager.Instance.HasEnemy(type))
		{
			Vector3 realLocation = tilemap.CellToWorld( position );
			GameObject instantiate = Instantiate( EnemyManager.Instance.GetEnemy( type ), realLocation, Quaternion.identity );
			if( instantiate.GetComponent<ObjectScript>() == null )
			{
				instantiate.AddComponent<ObjectScript>();
			}
			instantiate.SetActive(true);
			instantiate.GetComponent<SpriteRenderer>().flipX = RandomUtils.NextBool();
			instantiate.GetComponent<ObjectScript>().id = id;
			idToGameObject.Add( id, instantiate );
		}
	}

	public void SpawnItem( string id, string type, Vector3Int position )
	{
		if(ItemManager.Instance.HasItem(type))
		{
			Vector3 realLocation = tilemap.CellToWorld( position );
			GameObject instantiate = Instantiate( ItemManager.Instance.GetItem( type ), realLocation, Quaternion.identity );
			if( instantiate.GetComponent<ObjectScript>() == null )
			{
				instantiate.AddComponent<ObjectScript>();
			}
			instantiate.SetActive(true);
			instantiate.GetComponent<SpriteRenderer>().flipX = RandomUtils.NextBool();
			instantiate.GetComponent<ObjectScript>().id = id;
			idToGameObject.Add( id, instantiate );
		}
	}

	public void DestroyById(String id)
	{
		if (idToGameObject.ContainsKey(id))
		{
			Destroy(idToGameObject[id]);
			idToGameObject.Remove(id);
		}
	}

	public GameObject GetPlayerById(string id)
	{
		if (idToGameObject.ContainsKey(id))
		{
			return idToGameObject[id];
		}
		else
		{
			return null;
		}
	}

	public void GameEnd()
	{
		finalStatisticPanel.SetActive(true);
		int maxScore = -1;
		foreach (var value in playerIdToPlayerScore.Values)
		{
			maxScore = Math.Max(maxScore, value.Item1);
		}
		for (int i = 0; i < UserProperties.UserRoom.Players.Count; i++)
		{
			TMP_Text userName = playerFinalStatistic[i].transform.Find("UserName").GetComponent<TMP_Text>();
			userName.text = userStatistic[i].transform.Find("UserName").GetComponent<TMP_Text>().text;
			
			TMP_Text userScore = playerFinalStatistic[i].transform.Find("UserScore").GetComponent<TMP_Text>();
			userScore.text = userStatistic[i].transform.Find("UserScore").GetComponent<TMP_Text>().text;
			
			Image image = playerFinalStatistic[i].transform.Find("Image").GetComponent<Image>();
			StartCoroutine(RaiseImage(image, maxScore !=0 ? int.Parse(userScore.text)/maxScore: 0.1f));
		}

		for (int i = UserProperties.UserRoom.Players.Count; i < playerFinalStatistic.Count; i++)
		{
			playerFinalStatistic[i].SetActive(false);
		}
	}

	private IEnumerator RaiseImage(Image image, float upto)
	{
		float current = 0;
		while (current < upto)
		{
			try
			{
				current += Time.deltaTime * ImageRaiseSpeed;
				image.fillAmount = current;
			}
			catch (Exception)
			{
				//ignored
			}
			yield return current;
		}
		
	}

	public void BackToRoomScene()
	{
		UserProperties.UserRoom = new Room();
		Time.timeScale = 1;
		SceneManager.LoadScene("AvatarRoomSelectScene");
	}
}