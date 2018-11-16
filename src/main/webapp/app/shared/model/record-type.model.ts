import { IRecordItem } from 'app/shared/model//record-item.model';

export interface IRecordType {
    id?: number;
    number?: number;
    name?: string;
    trigger?: string;
    description?: string;
    recordItems?: IRecordItem[];
}

export class RecordType implements IRecordType {
    constructor(
        public id?: number,
        public number?: number,
        public name?: string,
        public trigger?: string,
        public description?: string,
        public recordItems?: IRecordItem[]
    ) {}
}
