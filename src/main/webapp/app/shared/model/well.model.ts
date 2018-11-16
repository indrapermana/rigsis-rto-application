import { Moment } from 'moment';
import { IWellbore } from 'app/shared/model//wellbore.model';

export const enum Status {
    Active = 'Active',
    Inactive = 'Inactive'
}

export interface IWell {
    id?: number;
    name?: string;
    legalName?: string;
    licenseDate?: Moment;
    licenseNumber?: string;
    govermentNumber?: string;
    apiNumber?: string;
    country?: string;
    block?: string;
    field?: string;
    district?: string;
    county?: string;
    state?: string;
    region?: string;
    operator?: string;
    operatorInterest?: number;
    operatorDivision?: string;
    timeZone?: string;
    status?: Status;
    purpose?: string;
    direction?: string;
    fluid?: string;
    dateTimeSpud?: Moment;
    dateTimePA?: Moment;
    headElevation?: number;
    groundElevation?: number;
    waterDepth?: number;
    latitude?: string;
    longitude?: string;
    wellbores?: IWellbore[];
}

export class Well implements IWell {
    constructor(
        public id?: number,
        public name?: string,
        public legalName?: string,
        public licenseDate?: Moment,
        public licenseNumber?: string,
        public govermentNumber?: string,
        public apiNumber?: string,
        public country?: string,
        public block?: string,
        public field?: string,
        public district?: string,
        public county?: string,
        public state?: string,
        public region?: string,
        public operator?: string,
        public operatorInterest?: number,
        public operatorDivision?: string,
        public timeZone?: string,
        public status?: Status,
        public purpose?: string,
        public direction?: string,
        public fluid?: string,
        public dateTimeSpud?: Moment,
        public dateTimePA?: Moment,
        public headElevation?: number,
        public groundElevation?: number,
        public waterDepth?: number,
        public latitude?: string,
        public longitude?: string,
        public wellbores?: IWellbore[]
    ) {}
}
