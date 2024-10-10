import { IGrad } from 'app/shared/model/grad.model';
import { ISkill } from 'app/shared/model/skill.model';

export interface IGradWithInfo {
  grad?: IGrad;
  graduationList?: any[] | null;
  skillList?: ISkill[] | null;
}

export const defaultValue: Readonly<IGradWithInfo> = {};
