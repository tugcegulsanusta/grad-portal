import { IGrad } from 'app/shared/model/grad.model';

export interface ISkill {
  id?: number;
  name?: string;
  description?: string | null;
  rate?: number | null;
  order?: number | null;
  gradId?: IGrad | null;
}

export const defaultValue: Readonly<ISkill> = {};
