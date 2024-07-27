export interface ISchool {
  id?: number;
  universityName?: string;
  facultyName?: string;
  departmentName?: string;
}

export const defaultValue: Readonly<ISchool> = {};
