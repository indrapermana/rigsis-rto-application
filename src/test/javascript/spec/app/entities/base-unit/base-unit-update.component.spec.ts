/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RtoAppTestModule } from '../../../test.module';
import { BaseUnitUpdateComponent } from 'app/entities/base-unit/base-unit-update.component';
import { BaseUnitService } from 'app/entities/base-unit/base-unit.service';
import { BaseUnit } from 'app/shared/model/base-unit.model';

describe('Component Tests', () => {
    describe('BaseUnit Management Update Component', () => {
        let comp: BaseUnitUpdateComponent;
        let fixture: ComponentFixture<BaseUnitUpdateComponent>;
        let service: BaseUnitService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [BaseUnitUpdateComponent]
            })
                .overrideTemplate(BaseUnitUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BaseUnitUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BaseUnitService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BaseUnit(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.baseUnit = entity;
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
                    const entity = new BaseUnit();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.baseUnit = entity;
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
