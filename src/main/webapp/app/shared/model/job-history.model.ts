import dayjs from 'dayjs';
import { IGrad } from 'app/shared/model/grad.model';

export interface IJobHistory {
  id?: number;
  companyName?: string;
  jobTitle?: string;
  startDate?: dayjs.Dayjs;
  endDate?: dayjs.Dayjs | null;
  isCurrent?: boolean;
  gradId?: IGrad | null;
}

export const defaultValue: Readonly<IJobHistory> = {
  isCurrent: false,
};
