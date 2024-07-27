import { ISchool } from 'app/shared/model/school.model';
import { IGrad } from 'app/shared/model/grad.model';

export interface IGraduation {
  id?: number;
  startYear?: number | null;
  graduationYear?: number;
  gpa?: number | null;
  schoolId?: ISchool | null;
  gradId?: IGrad | null;
}

export const defaultValue: Readonly<IGraduation> = {};
