package com.eltescode.user_domain.model

import java.util.UUID

data class CharacterSheet(
    val character: Character = Character(),
    val characterDescription: CharacterDescription = CharacterDescription(),
    val profile: Profile = Profile(),
    val weapons: List<Weapon> = emptyList(),
    val armors: List<Armor> = emptyList(),
    val armorPoints: ArmorPoints = ArmorPoints(),
    val player: Player = Player(),
    val experiencePoints: ExperiencePoints = ExperiencePoints(),
    val movementInCombat: MovementInCombat = MovementInCombat(),
    val skills: List<CharacterSkill> = emptyList(),
    val talents: List<Talent> = emptyList(),
    val equipment: List<Equipment> = emptyList(),
    val money: Money = Money(),
    val id: String = UUID.randomUUID().toString()
)

data class Character(
    val name: String = "",
    val race: String = "",
    val currentProfession: String = "",
    val previousProfession: String = ""
)

data class CharacterDescription(
    val age: String = "",
    val eyeColor: String = "",
    val hairColor: String = "",
    val starSign: String = "",
    val sex: String = "",
    val weight: String = "",
    val height: String = "",
    val siblings: String = "",
    val placeOfBirth: String = "",
    val specialSigns: String = "",
)

data class Profile(
    val characterMainProfile: CharacterMainProfile = CharacterMainProfile(),
    val secondaryProfile: CharacterSecondaryProfile = CharacterSecondaryProfile(),
    val professionMainProfile: ProfessionMainProfile = ProfessionMainProfile(),
    val professionSecondaryProfile: ProfessionSecondaryProfile = ProfessionSecondaryProfile()
)

data class CharacterMainProfile(
    val ww: Int = 0,
    val us: Int = 0,
    val k: Int = 0,
    val odp: Int = 0,
    val zr: Int = 0,
    val int: Int = 0,
    val sw: Int = 0,
    val ogd: Int = 0,
)

data class CharacterSecondaryProfile(
    val a: Int = 0,
    val zyw: Int = 0,
    val s: Int = 0,
    val wt: Int = 0,
    val sz: Int = 0,
    val mag: Int = 0,
    val po: Int = 0,
    val pp: Int = 0,
)

data class ProfessionMainProfile(
    val ww: Int = 0,
    val us: Int = 0,
    val k: Int = 0,
    val odp: Int = 0,
    val zr: Int = 0,
    val int: Int = 0,
    val sw: Int = 0,
    val ogd: Int = 0,
)

data class ProfessionSecondaryProfile(
    val a: Int = 0,
    val zyw: Int = 0,
    val s: Int = 0,
    val wt: Int = 0,
    val sz: Int = 0,
    val mag: Int = 0,
    val po: Int = 0,
    val pp: Int = 0,
)

data class Weapon(
    val name: String = "",
    val weight: Int = 0,
    val category: String = "",
    val weaponStrength: Int = 0,
    val range: Int? = null,
    val reloadDuration: String = "",
    val weaponFeatures: String = ""
)

data class Armor(
    val name: String = "",
    val weight: Int = 0,
    val bodyPart: String = "",
    val armorPoints: String = "",
)

data class ArmorPoints(
    val head: Int = 0,
    val body: Int = 0,
    val leftHand: Int = 0,
    val rightHand: Int = 0,
    val leftLeg: Int = 0,
    val rightLeg: Int = 0
)

data class Player(
    val name: String = "",
    val campaign: String = "",
    val year: String = "",
    val gameMaster: String = ""
)

data class ExperiencePoints(
    val currentExperience: Int = 0,
    val totalExperience: Int = 0,
)

data class MovementInCombat(
    val movement: String = "",
    val charge: String = "",
    val run: String = ""
)

data class Equipment(
    val name: String = "",
    val weight: Int = 0,
    val description: String = ""
)

data class Money(
    val gold: Int = 0,
    val silver: Int = 0,
    val brass: Int = 0
)

data class CharacterSkill(
    val skill: Skill = Skill(),
    val boughtOut: Boolean = false,
    val plusTen: Boolean = false,
    val plusTwenty: Boolean = false,
    val relatedTalents: String = ""
)

data class Skill(
    val skillName: String = "",
    val skillType: String = "",
    val characteristic: String = "",
    val description: String = "",
)

data class Talent(
    val name: String = "",
    val description: String = ""
)