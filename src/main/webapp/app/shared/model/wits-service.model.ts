export const enum Status {
    Active = 'Active',
    Inactive = 'Inactive'
}

export interface IWitsService {
    id?: number;
    name?: string;
    serviceType?: string;
    hostname?: string;
    port?: string;
    status?: Status;
}

export class WitsService implements IWitsService {
    constructor(
        public id?: number,
        public name?: string,
        public serviceType?: string,
        public hostname?: string,
        public port?: string,
        public status?: Status
    ) {}
}
