import { IRig } from 'app/shared/model//rig.model';

export interface IJob {
    id?: number;
    type?: string;
    rigs?: IRig[];
}

export class Job implements IJob {
    constructor(public id?: number, public type?: string, public rigs?: IRig[]) {}
}
