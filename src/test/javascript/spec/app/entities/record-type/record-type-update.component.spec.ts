/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RtoAppTestModule } from '../../../test.module';
import { RecordTypeUpdateComponent } from 'app/entities/record-type/record-type-update.component';
import { RecordTypeService } from 'app/entities/record-type/record-type.service';
import { RecordType } from 'app/shared/model/record-type.model';

describe('Component Tests', () => {
    describe('RecordType Management Update Component', () => {
        let comp: RecordTypeUpdateComponent;
        let fixture: ComponentFixture<RecordTypeUpdateComponent>;
        let service: RecordTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [RecordTypeUpdateComponent]
            })
                .overrideTemplate(RecordTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RecordTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecordTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RecordType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recordType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RecordType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recordType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
