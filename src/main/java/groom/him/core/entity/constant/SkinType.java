package groom.him.core.entity.constant;

public enum SkinType {
  OILY_TYPE_1("지성", "완전 기름진 피부", 1),
  OILY_TYPE_2("지성", "덜 기름진 피부", 2),
  OILY_TYPE_3("지성", "꽤 기름진 피부", 3),
  OILY_TYPE_4("지성", "그나마 기름진 피부", 4),

  DRY_TYPE_1("건성", "건조한 피부", 1),
  DRY_TYPE_2("건성", "건조한 피부", 2),
  DRY_TYPE_3("건성", "건조한 피부", 3),
  DRY_TYPE_4("건성", "건조한 피부", 4),

  COMBINATION_OILY_TYPE_1("수부지", "수분 부족 지성 피부", 1),
  COMBINATION_OILY_TYPE_2("수부지", "수분 부족 지성 피부", 2),
  COMBINATION_OILY_TYPE_3("수부지", "수분 부족 지성 피부", 3),
  COMBINATION_OILY_TYPE_4("수부지", "수분 부족 지성 피부", 4);

  SkinType(String 지성, String s, int i) {
  }
}
