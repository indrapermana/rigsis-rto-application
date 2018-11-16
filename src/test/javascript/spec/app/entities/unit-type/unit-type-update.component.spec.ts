/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RtoAppTestModule } from '../../../test.module';
import { UnitTypeUpdateComponent } from 'app/entities/unit-type/unit-type-update.component';
import { UnitTypeService } from 'app/entities/unit-type/unit-type.service';
import { UnitType } from 'app/shared/model/unit-type.model';

describe('Component Tests', () => {
    describe('UnitType Management Update Component', () => {
        let comp: UnitTypeUpdateComponent;
        let fixture: ComponentFixture<UnitTypeUpdateComponent>;
        let service: UnitTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [UnitTypeUpdateComponent]
            })
                .overrideTemplate(UnitTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UnitTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UnitTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new UnitType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.unitType = entity;
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
                    const entity = new UnitType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.unitType = entity;
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
