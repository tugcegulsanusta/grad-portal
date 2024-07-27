export interface IGrad {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
}

export const defaultValue: Readonly<IGrad> = {};
