import { Moment } from 'moment';
import { IWellbore } from 'app/shared/model//wellbore.model';
import { IJob } from 'app/shared/model//job.model';

export const enum BooleanType {
    Yes = 'Yes',
    No = 'No'
}

export interface IRig {
    id?: number;
    name?: string;
    offshore?: BooleanType;
    owner?: string;
    type?: string;
    rigClass?: string;
    manufacturer?: string;
    yearService?: number;
    approvals?: string;
    registration?: string;
    contact?: string;
    email?: string;
    phone?: string;
    fax?: string;
    drillDepth?: number;
    waterDepth?: number;
    airGap?: number;
    startDate?: Moment;
    endDate?: Moment;
    wellbore?: IWellbore;
    jobs?: IJob[];
}

export class Rig implements IRig {
    constructor(
        public id?: number,
        public name?: string,
        public offshore?: BooleanType,
        public owner?: string,
        public type?: string,
        public rigClass?: string,
        public manufacturer?: string,
        public yearService?: number,
        public approvals?: string,
        public registration?: string,
        public contact?: string,
        public email?: string,
        public phone?: string,
        public fax?: string,
        public drillDepth?: number,
        public waterDepth?: number,
        public airGap?: number,
        public startDate?: Moment,
        public endDate?: Moment,
        public wellbore?: IWellbore,
        public jobs?: IJob[]
    ) {}
}
